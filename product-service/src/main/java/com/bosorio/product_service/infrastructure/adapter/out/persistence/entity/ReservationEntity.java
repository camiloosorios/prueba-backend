package com.bosorio.product_service.infrastructure.adapter.out.persistence.entity;

import com.bosorio.product_service.domain.enums.ReservationState;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reservations")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationState status;

    @Column(nullable = false)
    private Long orderId;

}
