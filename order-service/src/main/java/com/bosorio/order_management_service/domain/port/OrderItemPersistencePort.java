package com.bosorio.order_management_service.domain.port;

import com.bosorio.order_management_service.domain.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemPersistencePort {

    OrderItem createOrderItem(OrderItem orderItem);
    Optional<OrderItem> getOrderItemById(Long id);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    OrderItem updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(OrderItem orderItem);

}
