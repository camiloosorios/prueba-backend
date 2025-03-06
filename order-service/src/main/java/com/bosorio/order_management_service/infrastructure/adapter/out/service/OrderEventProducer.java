package com.bosorio.order_management_service.infrastructure.adapter.out.service;

import com.bosorio.order_management_service.application.dto.events.ReservationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, ReservationEvent> reservationEventKafkaTemplate;

    public void sendReservationEvent(ReservationEvent event) {
        reservationEventKafkaTemplate.send("reservation-event", event);
    }

}
