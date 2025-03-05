package com.bosorio.order_management_service.infrastructure.adapter.out.mapper;

import com.bosorio.order_management_service.domain.model.Order;
import com.bosorio.order_management_service.domain.model.OrderItem;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderEntity;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderItemEntity;

import java.util.List;

import static com.bosorio.order_management_service.infrastructure.adapter.out.mapper.OrderItemEntityMapper.fromOrderItemEntityToOrderItem;

public class OrderEntityMapper {

    public static OrderEntity fromOrderToOrderEntity(Order orderDto) {
        OrderEntity order = new OrderEntity();
        order.setId(orderDto.getId());
        order.setUserId(orderDto.getUserId());
        order.setStatus(orderDto.getStatus());
        order.setVersion(orderDto.getVersion());

        return order;
    }

    public static Order fromOrderEntityToOrder(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setUserId(orderEntity.getUserId());
        order.setStatus(orderEntity.getStatus());
        order.setVersion(orderEntity.getVersion());

        return order;
    }

}
