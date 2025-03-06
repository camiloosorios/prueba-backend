package com.bosorio.product_service.infrastructure.adapter.out.service;

import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.application.dto.ReservationEvent;
import com.bosorio.product_service.application.enums.ReservationEventType;
import com.bosorio.product_service.application.service.ReservationService;
import com.bosorio.product_service.domain.enums.ReservationState;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEventConsumer {

    private final ReservationService reservationService;

    @KafkaListener(topics = "reservation-event", groupId = "product-group")
    public void consumeReservationEvent(ReservationEvent event) {
        if (event.getEventType() == ReservationEventType.CREATE) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setOrderId(event.getOrderId());
            reservationDto.setProductId(event.getProductId());
            reservationDto.setQuantity(event.getQuantity());
            reservationService.createReservation(reservationDto);
        } else if (event.getEventType() == ReservationEventType.CANCEL) {
            reservationService.updateReservation(event.getId(), ReservationState.CANCELLED);
        }
    }

}
