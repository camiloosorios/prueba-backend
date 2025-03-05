package com.bosorio.order_management_service.infrastructure.adapter.out;

import com.bosorio.order_management_service.domain.model.Order;
import com.bosorio.order_management_service.domain.port.OrderPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderEntityMapper;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderEntity;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderEntityMapper.fromOrderEntityToOrder;
import static com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderEntityMapper.fromOrderToOrderEntity;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = fromOrderToOrderEntity(order);

        return fromOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderEntityMapper::fromOrderEntityToOrder);
    }

    @Override
    public List<Order> findAllUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderEntityMapper::fromOrderEntityToOrder)
                .toList();
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity orderEntity = fromOrderToOrderEntity(order);

        return fromOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public void deleteOrder(Order order) {
        OrderEntity orderEntity = fromOrderToOrderEntity(order);
        orderRepository.delete(orderEntity);
    }
}
