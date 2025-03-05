package com.bosorio.product_service.domain.port;

import com.bosorio.product_service.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationPersistencePort {

    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    Optional<Reservation> findByIdForUpdate(Long id);
    Optional<Reservation> findByOrderIdAndProductId(Long orderId, Long productId);
    List<Reservation> findAllOrderReservations(Long orderId);
    Reservation update(Reservation reservation);
    void delete(Reservation reservation);

}
