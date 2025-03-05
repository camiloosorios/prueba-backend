package com.bosorio.order_management_service.application.dto.order;

import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.domain.enums.OrderState;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderDto {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Order status is required")
    private OrderState orderState;

    @NotNull(message = "Order version is required")
    private Integer version;
}
