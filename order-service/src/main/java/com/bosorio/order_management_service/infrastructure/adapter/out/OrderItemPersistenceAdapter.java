package com.bosorio.order_management_service.infrastructure.adapter.out;

import com.bosorio.order_management_service.domain.model.OrderItem;
import com.bosorio.order_management_service.domain.port.OrderItemPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderItemEntityMapper;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderItemEntityMapper.fromOrderItemEntityToOrderItem;
import static com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderItemEntityMapper.fromOrderItemToOrderItemEntity;

@Component
@RequiredArgsConstructor
public class OrderItemPersistenceAdapter implements OrderItemPersistencePort {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        OrderItemEntity orderEntity = fromOrderItemToOrderItemEntity(orderItem);

        return fromOrderItemEntityToOrderItem(orderItemRepository.save(orderEntity));
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .map(OrderItemEntityMapper::fromOrderItemEntityToOrderItem);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemEntityMapper::fromOrderItemEntityToOrderItem)
                .toList();
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        OrderItemEntity orderEntity = fromOrderItemToOrderItemEntity(orderItem);

        return fromOrderItemEntityToOrderItem(orderItemRepository.save(orderEntity));
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = fromOrderItemToOrderItemEntity(orderItem);
        orderItemRepository.delete(orderItemEntity);
    }
}
