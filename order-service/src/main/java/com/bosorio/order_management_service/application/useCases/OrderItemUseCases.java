package com.bosorio.order_management_service.application.useCases;

import com.bosorio.order_management_service.application.dto.orderItem.CreateOrderItemDto;
import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;

import java.util.List;

public interface OrderItemUseCases {

    void createOrderItem(CreateOrderItemDto orderItem, Long orderId);
    OrderItemDto findOrderItemById(Long id);
    List<OrderItemDto> findOrderItemsByOrderId(Long orderId);
    OrderItemDto updateOrderItem(OrderItemDto orderItemDto);
    void deleteOrderItem(Long id);

}
