package com.bosorio.product_service.application.dto;

import com.bosorio.product_service.application.enums.ReservationEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationEvent {

    private Long id;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private ReservationEventType eventType;

}