package com.bosorio.product_service.infrastructure.adapter.out.persistence.repository;

import com.bosorio.product_service.infrastructure.adapter.out.persistence.entity.ReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReservationEntity r WHERE r.id = :id")
    Optional<ReservationEntity> findByIdForUpdate(Long id);

    List<ReservationEntity> findByOrderId(Long orderId);

    @Query("SELECT r FROM ReservationEntity r WHERE r.orderId = :orderId AND r.productId = :productId")
    Optional<ReservationEntity> findByOrderIdAndProductId(Long orderId, Long productId);
}
