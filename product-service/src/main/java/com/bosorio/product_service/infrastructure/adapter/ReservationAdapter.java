package com.bosorio.product_service.infrastructure.adapter;

import com.bosorio.product_service.domain.model.Reservation;
import com.bosorio.product_service.domain.port.ReservationPersistencePort;
import com.bosorio.product_service.infrastructure.adapter.out.mapper.ReservationEntityMapper;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.entity.ReservationEntity;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.bosorio.product_service.infrastructure.adapter.out.mapper.ReservationEntityMapper.fromReservationEntityToReservation;
import static com.bosorio.product_service.infrastructure.adapter.out.mapper.ReservationEntityMapper.fromReservationToReservationEntity;

@Component
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationPersistencePort {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = reservationRepository.save(fromReservationToReservationEntity(reservation));
        return  fromReservationEntityToReservation(reservationEntity);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationEntityMapper::fromReservationEntityToReservation);
    }

    @Override
    public Optional<Reservation> findByIdForUpdate(Long id) {
        return reservationRepository.findByIdForUpdate(id)
                .map(ReservationEntityMapper::fromReservationEntityToReservation);
    }

    @Override
    public Optional<Reservation> findByOrderIdAndProductId(Long orderId, Long productId) {
        return reservationRepository.findByOrderIdAndProductId(orderId, productId)
                .map(ReservationEntityMapper::fromReservationEntityToReservation);
    }

    @Override
    public List<Reservation> findAllOrderReservations(Long orderId) {
        return reservationRepository.findByOrderId(orderId)
                .stream()
                .map(ReservationEntityMapper::fromReservationEntityToReservation)
                .toList();
    }

    @Override
    public Reservation update(Reservation reservation) {
        ReservationEntity reservationEntity = reservationRepository.save(fromReservationToReservationEntity(reservation));
        return  fromReservationEntityToReservation(reservationEntity);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(fromReservationToReservationEntity(reservation));
    }
}
