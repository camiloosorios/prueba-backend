package com.bosorio.product_service.domain.model;

import com.bosorio.product_service.domain.business.ReservationStateMachine;
import com.bosorio.product_service.domain.enums.ReservationState;

public class Reservation {

    private Long id;

    private Long productId;

    private Integer quantity;

    private ReservationState status;

    private Long orderId;

    public Reservation() {
        status = ReservationState.ACTIVE;
    }

    public Reservation(Long id, Long productId, Integer quantity, ReservationState status, Long orderId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.orderId = orderId;
    }

    //Business Logic

    public void complete() {
        ReservationStateMachine stateMachine = new ReservationStateMachine(this.status);
        stateMachine.complete();
        this.status = stateMachine.getCurrentState();
    }

    public void cancel() {
        ReservationStateMachine stateMachine = new ReservationStateMachine(this.status);
        stateMachine.cancel();
        this.status = stateMachine.getCurrentState();
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ReservationState getStatus() {
        return status;
    }

    public void setStatus(ReservationState status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
