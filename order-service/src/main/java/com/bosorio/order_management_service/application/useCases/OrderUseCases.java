package com.bosorio.order_management_service.application.useCases;

import com.bosorio.order_management_service.application.dto.order.CreateOrderDto;
import com.bosorio.order_management_service.application.dto.order.OrderDto;
import com.bosorio.order_management_service.application.dto.order.UpdateOrderDto;

import java.util.List;

public interface OrderUseCases {

    OrderDto createOrder(CreateOrderDto orderDto);
    OrderDto findOrderById(Long id);
    List<OrderDto> findAllUserOrders(Long userId);
    OrderDto updateOrder(Long id, UpdateOrderDto orderDto);
    void deleteOrder(Long id);

}
