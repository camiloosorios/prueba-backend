package com.bosorio.product_service.application.dto;

import com.bosorio.product_service.domain.enums.ReservationState;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDto {

    private Long id;

    @NotNull(message = "Product is is required")
    private Long productId;

    @NotNull(message = "quantity is required")
    private Integer quantity;

    private ReservationState status;

    @NotNull(message = "Order id is required")
    private Long orderId;

}
