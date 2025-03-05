package com.bosorio.product_service.application.service;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.domain.exception.BadRequestException;
import com.bosorio.product_service.application.exception.NotFoundException;
import com.bosorio.product_service.application.mapper.ReservationMapper;
import com.bosorio.product_service.application.usecases.ReservationUseCases;
import com.bosorio.product_service.domain.enums.ReservationState;
import com.bosorio.product_service.domain.model.Reservation;
import com.bosorio.product_service.domain.port.ReservationPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bosorio.product_service.application.mapper.ReservationMapper.fromReservationDtoToReservation;
import static com.bosorio.product_service.application.mapper.ReservationMapper.fromReservationToReservationDto;

@Service
@RequiredArgsConstructor
public class ReservationService implements ReservationUseCases {

    private final ReservationPersistencePort reservationPersistencePort;

    private final ProductService productService;

    @Override
    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        ProductDto productDto = productService.getProductByIdForUpdate(reservationDto.getProductId());

        if (productDto.getStock() < reservationDto.getQuantity()) {
            throw new BadRequestException("Insufficient stock");
        }

        reservationDto.setStatus(ReservationState.ACTIVE);

        productService.updateProduct(productDto.getId(), new CreateProductDto(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock() - reservationDto.getQuantity()
        ));

        Reservation reservation = reservationPersistencePort.save(fromReservationDtoToReservation(reservationDto));

        return fromReservationToReservationDto(reservation);
    }

    @Override
    public ReservationDto getReservation(Long id) {
        return reservationPersistencePort.findById(id)
                .map(ReservationMapper::fromReservationToReservationDto)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
    }

    @Override
    public List<ReservationDto> getReservationsByOrderId(Long orderId) {
        return reservationPersistencePort.findAllOrderReservations(orderId)
                .stream()
                .map(ReservationMapper::fromReservationToReservationDto)
                .toList();
    }

    @Override
    public ReservationDto getReservationByOrderIdAndProductId(Long orderId, Long productId) {
        return reservationPersistencePort.findByOrderIdAndProductId(orderId, productId)
                .map(ReservationMapper::fromReservationToReservationDto)
                .orElse(null);
    }


    @Override
    @Transactional
    public ReservationDto updateReservation(Long id, ReservationState reservationState) {
        Reservation reservation = reservationPersistencePort.findByIdForUpdate(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        switch (reservationState) {
            case COMPLETED -> reservation.complete();
            case CANCELLED -> reservation.cancel();
        }

        if (reservation.getStatus() == ReservationState.CANCELLED) {
            ProductDto productDto = productService.getProductById(reservation.getProductId());
            productDto.setStock(productDto.getStock() + reservation.getQuantity());
            productService.updateProduct(productDto.getId(), new CreateProductDto(
                    productDto.getName(),
                    productDto.getDescription(),
                    productDto.getPrice(),
                    productDto.getStock()
            ));
        }

        return fromReservationToReservationDto(reservationPersistencePort.update(reservation));
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationPersistencePort.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        reservationPersistencePort.delete(reservation);
    }
}
