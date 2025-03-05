package com.bosorio.order_management_service.application.dto.order;

import com.bosorio.order_management_service.domain.enums.OrderState;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private Long id;

    private Long userId;

    private OrderState status;

    private Integer version;

    public OrderDto(Long id) {
        this.id = id;
    }
}
