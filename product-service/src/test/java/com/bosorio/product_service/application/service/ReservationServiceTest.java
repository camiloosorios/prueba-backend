package com.bosorio.product_service.application.service;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.application.exception.NotFoundException;
import com.bosorio.product_service.application.mapper.ReservationMapper;
import com.bosorio.product_service.domain.enums.ReservationState;
import com.bosorio.product_service.domain.exception.BadRequestException;
import com.bosorio.product_service.domain.model.Reservation;
import com.bosorio.product_service.domain.port.ReservationPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationPersistencePort reservationPersistencePort;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationDto reservationDto;
    private ProductDto productDto;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setOrderId(1L);
        reservationDto.setProductId(1L);
        reservationDto.setQuantity(5);
        reservationDto.setStatus(ReservationState.ACTIVE);

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(BigDecimal.valueOf(100.0));
        productDto.setStock(10);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setOrderId(1L);
        reservation.setProductId(1L);
        reservation.setQuantity(5);
        reservation.setStatus(ReservationState.ACTIVE);
    }

    @Test
    void createReservationShouldReturnReservationDto() {
        when(productService.getProductByIdForUpdate(anyLong())).thenReturn(productDto);
        when(reservationPersistencePort.save(any(Reservation.class))).thenReturn(reservation);

        ReservationDto result = reservationService.createReservation(reservationDto);

        assertNotNull(result);
        assertEquals(reservationDto.getId(), result.getId());
        assertEquals(reservationDto.getOrderId(), result.getOrderId());
        assertEquals(reservationDto.getProductId(), result.getProductId());
        assertEquals(reservationDto.getQuantity(), result.getQuantity());
        assertEquals(reservationDto.getStatus(), result.getStatus());

        verify(productService, times(1)).getProductByIdForUpdate(anyLong());
        verify(reservationPersistencePort, times(1)).save(any(Reservation.class));
    }

    @Test
    void createReservationShouldThrowBadRequestExceptionWhenInsufficientStock() {
        productDto.setStock(3);
        when(productService.getProductByIdForUpdate(anyLong())).thenReturn(productDto);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            reservationService.createReservation(reservationDto);
        });

        assertEquals("Insufficient stock", exception.getMessage());
        verify(productService, times(1)).getProductByIdForUpdate(anyLong());
        verify(reservationPersistencePort, never()).save(any(Reservation.class));
    }

    @Test
    void getReservationShouldReturnReservationDto() {
        when(reservationPersistencePort.findById(anyLong())).thenReturn(Optional.of(reservation));

        ReservationDto result = reservationService.getReservation(1L);

        assertNotNull(result);
        assertEquals(reservationDto.getId(), result.getId());
        assertEquals(reservationDto.getOrderId(), result.getOrderId());
        assertEquals(reservationDto.getProductId(), result.getProductId());
        assertEquals(reservationDto.getQuantity(), result.getQuantity());
        assertEquals(reservationDto.getStatus(), result.getStatus());

        verify(reservationPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void getReservationShouldThrowNotFoundException() {
        when(reservationPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            reservationService.getReservation(1L);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void getReservationsByOrderIdShouldReturnListOfReservationDto() {
        when(reservationPersistencePort.findAllOrderReservations(anyLong())).thenReturn(Collections.singletonList(reservation));

        List<ReservationDto> result = reservationService.getReservationsByOrderId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservationDto.getId(), result.get(0).getId());
        assertEquals(reservationDto.getOrderId(), result.get(0).getOrderId());
        assertEquals(reservationDto.getProductId(), result.get(0).getProductId());
        assertEquals(reservationDto.getQuantity(), result.get(0).getQuantity());
        assertEquals(reservationDto.getStatus(), result.get(0).getStatus());

        verify(reservationPersistencePort, times(1)).findAllOrderReservations(anyLong());
    }

    @Test
    void getReservationByOrderIdAndProductIdShouldReturnReservationDto() {
        when(reservationPersistencePort.findByOrderIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.of(reservation));

        ReservationDto result = reservationService.getReservationByOrderIdAndProductId(1L, 1L);

        assertNotNull(result);
        assertEquals(reservationDto.getId(), result.getId());
        assertEquals(reservationDto.getOrderId(), result.getOrderId());
        assertEquals(reservationDto.getProductId(), result.getProductId());
        assertEquals(reservationDto.getQuantity(), result.getQuantity());
        assertEquals(reservationDto.getStatus(), result.getStatus());

        verify(reservationPersistencePort, times(1)).findByOrderIdAndProductId(anyLong(), anyLong());
    }

    @Test
    void getReservationByOrderIdAndProductIdShouldReturnNull() {
        when(reservationPersistencePort.findByOrderIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ReservationDto result = reservationService.getReservationByOrderIdAndProductId(1L, 1L);

        assertNull(result);
        verify(reservationPersistencePort, times(1)).findByOrderIdAndProductId(anyLong(), anyLong());
    }

    @Test
    void updateReservationShouldReturnUpdatedReservationDto() {
        when(reservationPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.of(reservation));
        when(reservationPersistencePort.update(any(Reservation.class))).thenReturn(reservation);

        ReservationDto result = reservationService.updateReservation(1L, ReservationState.COMPLETED);

        assertNotNull(result);
        assertEquals(reservationDto.getId(), result.getId());
        assertEquals(reservationDto.getOrderId(), result.getOrderId());
        assertEquals(reservationDto.getProductId(), result.getProductId());
        assertEquals(reservationDto.getQuantity(), result.getQuantity());
        assertEquals(ReservationState.COMPLETED, result.getStatus());

        verify(reservationPersistencePort, times(1)).findByIdForUpdate(anyLong());
        verify(reservationPersistencePort, times(1)).update(any(Reservation.class));
    }

    @Test
    void updateReservationShouldThrowNotFoundException() {
        when(reservationPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            reservationService.updateReservation(1L, ReservationState.COMPLETED);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationPersistencePort, times(1)).findByIdForUpdate(anyLong());
        verify(reservationPersistencePort, never()).update(any(Reservation.class));
    }

    @Test
    void deleteReservationShouldDeleteReservation() {
        when(reservationPersistencePort.findById(anyLong())).thenReturn(Optional.of(reservation));
        doNothing().when(reservationPersistencePort).delete(any(Reservation.class));

        reservationService.deleteReservation(1L);

        verify(reservationPersistencePort, times(1)).findById(anyLong());
        verify(reservationPersistencePort, times(1)).delete(any(Reservation.class));
    }

    @Test
    void deleteReservationShouldThrowNotFoundException() {
        when(reservationPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            reservationService.deleteReservation(1L);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationPersistencePort, times(1)).findById(anyLong());
        verify(reservationPersistencePort, never()).delete(any(Reservation.class));
    }
}