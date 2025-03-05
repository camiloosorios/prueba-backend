package com.bosorio.order_management_service.application.mapper;

import com.bosorio.order_management_service.application.dto.order.OrderDto;
import com.bosorio.order_management_service.domain.model.Order;

public class OrderMapper {

    public static Order fromOrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUserId(orderDto.getUserId());
        order.setStatus(orderDto.getStatus());
        order.setVersion(orderDto.getVersion());

        return order;
    }

    public static OrderDto fromOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUserId());
        orderDto.setStatus(order.getStatus());
        orderDto.setVersion(order.getVersion());

        return orderDto;
    }

}
