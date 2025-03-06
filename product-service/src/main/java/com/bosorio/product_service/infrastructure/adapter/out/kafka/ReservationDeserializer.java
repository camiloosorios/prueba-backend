package com.bosorio.product_service.infrastructure.adapter.out.kafka;

import com.bosorio.product_service.application.dto.ReservationEvent;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReservationDeserializer implements Deserializer<ReservationEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReservationEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ReservationEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando el mensaje", e);
        }
    }
}
