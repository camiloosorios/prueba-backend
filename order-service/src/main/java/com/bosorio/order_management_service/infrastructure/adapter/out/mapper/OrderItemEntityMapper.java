package com.bosorio.order_management_service.infrastructure.adapter.out.mapper;

import com.bosorio.order_management_service.domain.model.Order;
import com.bosorio.order_management_service.domain.model.OrderItem;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderEntity;
import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderItemEntity;

public class OrderItemEntityMapper {

    public static OrderItemEntity fromOrderItemToOrderItemEntity(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(orderItem.getId());
        orderItemEntity.setProductId(orderItem.getProductId());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setOrderId(orderItem.getOrderId());
        orderItemEntity.setVersion(orderItem.getVersion());

        return orderItemEntity;
    }

    public static OrderItem fromOrderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        OrderItem orderItemDto = new OrderItem();
        orderItemDto.setId(orderItemEntity.getId());
        orderItemDto.setProductId(orderItemEntity.getProductId());
        orderItemDto.setQuantity(orderItemEntity.getQuantity());
        orderItemDto.setOrderId(orderItemEntity.getOrderId());
        orderItemDto.setVersion(orderItemEntity.getVersion());

        return orderItemDto;
    }

}
