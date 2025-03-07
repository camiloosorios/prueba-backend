package com.bosorio.order_management_service.application.service;

import com.bosorio.order_management_service.application.dto.order.CreateOrderDto;
import com.bosorio.order_management_service.application.dto.order.OrderDto;
import com.bosorio.order_management_service.application.dto.order.UpdateOrderDto;
import com.bosorio.order_management_service.application.dto.user.UserDto;
import com.bosorio.order_management_service.application.exception.NotFoundException;
import com.bosorio.order_management_service.domain.enums.OrderState;
import com.bosorio.order_management_service.domain.model.Order;
import com.bosorio.order_management_service.domain.port.OrderPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.OrderEventProducer;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.ProductService;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.UserService;
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
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderPersistencePort orderPersistencePort;

    @Mock
    private UserService userService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderEventProducer orderEventProducer;

    @InjectMocks
    private OrderService orderService;

    private CreateOrderDto createOrderDto;
    private Order order;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createOrderDto = new CreateOrderDto();
        createOrderDto.setUserId(1L);
        createOrderDto.setOrderItems(Collections.emptyList());

        userDto = new UserDto();
        userDto.setId(1L);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
    }

    @Test
    void createOrderShouldReturnOrderDto() {
        when(userService.getUserById(anyLong())).thenReturn(userDto);
        when(orderPersistencePort.createOrder(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.createOrder(createOrderDto);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(userService, times(1)).getUserById(anyLong());
        verify(orderPersistencePort, times(1)).createOrder(any(Order.class));
    }

    @Test
    void findOrderByIdShouldReturnOrderDto() {
        when(orderPersistencePort.findOrderById(anyLong())).thenReturn(Optional.of(order));

        OrderDto result = orderService.findOrderById(1L);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderPersistencePort, times(1)).findOrderById(anyLong());
    }

    @Test
    void findOrderByIdShouldThrowNotFoundException() {
        when(orderPersistencePort.findOrderById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findOrderById(1L));
        verify(orderPersistencePort, times(1)).findOrderById(anyLong());
    }

    @Test
    void findAllUserOrdersShouldReturnListOfOrderDto() {
        when(orderPersistencePort.findAllUserOrders(anyLong())).thenReturn(Collections.singletonList(order));

        List<OrderDto> result = orderService.findAllUserOrders(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderPersistencePort, times(1)).findAllUserOrders(anyLong());
    }

    @Test
    void updateOrderShouldReturnUpdatedOrderDto() {
        UpdateOrderDto updateOrderDto = new UpdateOrderDto();
        updateOrderDto.setOrderState(OrderState.CONFIRMED);
        updateOrderDto.setUserId(1L);
        updateOrderDto.setVersion(1);

        when(orderPersistencePort.findOrderById(anyLong())).thenReturn(Optional.of(order));
        when(orderPersistencePort.updateOrder(any(Order.class))).thenReturn(order);
        when(orderItemService.findOrderItemsByOrderId(anyLong())).thenReturn(Collections.emptyList());

        OrderDto result = orderService.updateOrder(1L, updateOrderDto);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderPersistencePort, times(1)).findOrderById(anyLong());
        verify(orderPersistencePort, times(1)).updateOrder(any(Order.class));
        verify(orderItemService, times(1)).findOrderItemsByOrderId(anyLong());
    }

    @Test
    void deleteOrderShouldDeleteOrder() {
        when(orderPersistencePort.findOrderById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(orderPersistencePort).deleteOrder(any(Order.class));

        orderService.deleteOrder(1L);

        verify(orderPersistencePort, times(1)).findOrderById(anyLong());
        verify(orderPersistencePort, times(1)).deleteOrder(any(Order.class));
    }
}