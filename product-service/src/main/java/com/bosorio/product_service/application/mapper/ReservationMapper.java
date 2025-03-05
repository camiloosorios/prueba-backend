package com.bosorio.product_service.application.mapper;

import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.domain.model.Reservation;

public class ReservationMapper {

    public static ReservationDto fromReservationToReservationDto(Reservation reservation) {
        return  new ReservationDto(
                reservation.getId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getOrderId()
        );
    }

    public static Reservation fromReservationDtoToReservation(ReservationDto reservationDto) {
        return new Reservation(
                reservationDto.getId(),
                reservationDto.getProductId(),
                reservationDto.getQuantity(),
                reservationDto.getStatus(),
                reservationDto.getOrderId()
        );
    }

}
