package com.bosorio.order_management_service.application.dto.reservation;

import com.bosorio.order_management_service.domain.enums.ReservationState;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDto {

    private Long id;

    private Long productId;

    private Integer quantity;

    private ReservationState status;

    private Long orderId;

}
