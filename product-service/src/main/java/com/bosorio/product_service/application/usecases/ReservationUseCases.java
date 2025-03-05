package com.bosorio.product_service.application.usecases;

import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.domain.enums.ReservationState;

import java.util.List;

public interface ReservationUseCases {

    ReservationDto createReservation(ReservationDto reservationDto);
    ReservationDto getReservation(Long id);
    List<ReservationDto> getReservationsByOrderId(Long orderId);
    ReservationDto getReservationByOrderIdAndProductId(Long orderId, Long productId);
    ReservationDto updateReservation(Long id, ReservationState reservationState);
    void deleteReservation(Long id);

}
