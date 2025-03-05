package com.bosorio.product_service.domain.business;

import com.bosorio.product_service.domain.enums.ReservationState;
import com.bosorio.product_service.domain.exception.BadRequestException;

public class ReservationStateMachine {

    private ReservationState currentState;

    public ReservationStateMachine(ReservationState currentState) {
        this.currentState = currentState;
    }

    public ReservationState getCurrentState() {
        return currentState;
    }

    public void complete() {
        if (currentState == ReservationState.CANCELLED) {
            throw new BadRequestException("Invalid reservation state");
        }
        currentState = ReservationState.COMPLETED;
    }

    public void cancel() {
        if (currentState == ReservationState.COMPLETED) {
            throw new BadRequestException("Invalid reservation state");
        }
        currentState = ReservationState.CANCELLED;
    }
}
