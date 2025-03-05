package com.bosorio.order_management_service.application.mapper;

import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.domain.model.OrderItem;

public class OrderItemMapper {

    public static OrderItemDto fromOrderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setOrderId(orderItem.getOrderId());
        orderItemDto.setVersion(orderItem.getVersion());

        return orderItemDto;
    }


}
