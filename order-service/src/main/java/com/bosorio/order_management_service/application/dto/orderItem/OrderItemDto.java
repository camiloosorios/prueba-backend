package com.bosorio.order_management_service.application.dto.orderItem;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemDto {

    private Long id;

    private Long productId;

    private Integer quantity;

    private Long orderId;

    private Integer version;

}
