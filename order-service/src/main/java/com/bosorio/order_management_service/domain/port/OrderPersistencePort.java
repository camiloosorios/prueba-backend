package com.bosorio.order_management_service.domain.port;

import com.bosorio.order_management_service.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPersistencePort {

    Order createOrder(Order order);
    Optional<Order> findOrderById(Long id);
    List<Order> findAllUserOrders(Long userId);
    Order updateOrder(Order order);
    void deleteOrder(Order order);

}
