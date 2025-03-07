package com.bosorio.order_management_service.application.service;

import com.bosorio.order_management_service.application.dto.orderItem.CreateOrderItemDto;
import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.application.dto.product.ProductDto;
import com.bosorio.order_management_service.application.exception.NotFoundException;
import com.bosorio.order_management_service.domain.exception.BadRequestException;
import com.bosorio.order_management_service.domain.model.OrderItem;
import com.bosorio.order_management_service.domain.port.OrderItemPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class OrderItemServiceTest {

    @Mock
    private OrderItemPersistencePort orderItemPersistencePort;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderItemService orderItemService;

    private CreateOrderItemDto createOrderItemDto;
    private OrderItem orderItem;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createOrderItemDto = new CreateOrderItemDto();
        createOrderItemDto.setProductId(1L);
        createOrderItemDto.setQuantity(5);

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setStock(10);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductId(1L);
        orderItem.setQuantity(5);
        orderItem.setOrderId(1L);
    }

    @Test
    void createOrderItemShouldCreateOrderItem() {
        when(productService.getProductByIdNoCache(anyLong())).thenReturn(productDto);
        when(orderItemPersistencePort.createOrderItem(any(OrderItem.class))).thenReturn(orderItem); // Simula el retorno

        orderItemService.createOrderItem(createOrderItemDto, 1L);

        verify(productService, times(1)).getProductByIdNoCache(anyLong());
        verify(orderItemPersistencePort, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void createOrderItemShouldThrowBadRequestException_WhenInsufficientStock() {
        productDto.setStock(3);
        when(productService.getProductByIdNoCache(anyLong())).thenReturn(productDto);

        assertThrows(BadRequestException.class, () -> {
            orderItemService.createOrderItem(createOrderItemDto, 1L);
        });

        verify(productService, times(1)).getProductByIdNoCache(anyLong());
        verify(orderItemPersistencePort, never()).createOrderItem(any(OrderItem.class));
    }

    @Test
    void findOrderItemByIdShouldReturnOrderItemDto() {
        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.of(orderItem));

        OrderItemDto result = orderItemService.findOrderItemById(1L);

        assertNotNull(result);
        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
    }

    @Test
    void findOrderItemByIdShouldThrowNotFoundException() {
        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderItemService.findOrderItemById(1L);
        });

        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
    }

    @Test
    void findOrderItemsByOrderIdShouldReturnListOfOrderItemDto() {
        when(orderItemPersistencePort.getOrderItemsByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

        List<OrderItemDto> result = orderItemService.findOrderItemsByOrderId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderItem.getId(), result.get(0).getId());
        verify(orderItemPersistencePort, times(1)).getOrderItemsByOrderId(anyLong());
    }

    @Test
    void updateOrderItemShouldReturnUpdatedOrderItemDto() {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(1L);
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(10);

        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.of(orderItem));
        when(productService.getProductById(anyLong())).thenReturn(productDto);
        when(orderItemPersistencePort.updateOrderItem(any(OrderItem.class))).thenReturn(orderItem);

        OrderItemDto result = orderItemService.updateOrderItem(orderItemDto);

        assertNotNull(result);
        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
        verify(productService, times(1)).getProductById(anyLong());
        verify(orderItemPersistencePort, times(1)).updateOrderItem(any(OrderItem.class));
    }

    @Test
    void updateOrderItemShouldThrowNotFoundException() {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(1L);

        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderItemService.updateOrderItem(orderItemDto);
        });

        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
        verify(productService, never()).getProductById(anyLong());
        verify(orderItemPersistencePort, never()).updateOrderItem(any(OrderItem.class));
    }

    @Test
    void deleteOrderItemShouldDeleteOrderItem() {
        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.of(orderItem));
        doNothing().when(orderItemPersistencePort).deleteOrderItem(any(OrderItem.class));

        orderItemService.deleteOrderItem(1L);

        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
        verify(orderItemPersistencePort, times(1)).deleteOrderItem(any(OrderItem.class));
    }

    @Test
    void deleteOrderItemShouldThrowNotFoundException() {
        when(orderItemPersistencePort.getOrderItemById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderItemService.deleteOrderItem(1L);
        });

        verify(orderItemPersistencePort, times(1)).getOrderItemById(anyLong());
        verify(orderItemPersistencePort, never()).deleteOrderItem(any(OrderItem.class));
    }
}