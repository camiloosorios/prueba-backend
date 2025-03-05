package com.bosorio.product_service.infrastructure.adapter.out.mapper;

import com.bosorio.product_service.domain.enums.ReservationState;
import com.bosorio.product_service.domain.model.Reservation;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.entity.ReservationEntity;

public class ReservationEntityMapper {

    public static ReservationEntity fromReservationToReservationEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getOrderId()
        );
    }

    public static Reservation fromReservationEntityToReservation(ReservationEntity reservationEntity) {
        return new Reservation(
                reservationEntity.getId(),
                reservationEntity.getProductId(),
                reservationEntity.getQuantity(),
                reservationEntity.getStatus(),
                reservationEntity.getOrderId()
        );
    }

}