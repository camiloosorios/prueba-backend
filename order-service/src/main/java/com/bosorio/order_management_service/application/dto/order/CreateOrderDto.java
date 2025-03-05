package com.bosorio.order_management_service.application.dto.order;

import com.bosorio.order_management_service.application.dto.orderItem.CreateOrderItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateOrderDto {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Order items are required")
    private List<CreateOrderItemDto> orderItems;

}
