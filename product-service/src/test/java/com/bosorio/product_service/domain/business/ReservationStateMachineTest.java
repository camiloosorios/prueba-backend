package com.bosorio.product_service.domain.business;

import com.bosorio.product_service.domain.enums.ReservationState;
import com.bosorio.product_service.domain.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationStateMachineTest {

    private ReservationStateMachine reservationStateMachine;

    @BeforeEach
    void setUp() {
        reservationStateMachine = new ReservationStateMachine(ReservationState.ACTIVE);
    }

    @Test
    void completeShouldChangeStateToCompletedWhenCurrentStateIsActive() {
        reservationStateMachine = new ReservationStateMachine(ReservationState.ACTIVE);

        reservationStateMachine.complete();

        assertEquals(ReservationState.COMPLETED, reservationStateMachine.getCurrentState());
    }

    @Test
    void completeShouldThrowBadRequestExceptionWhenCurrentStateIsCancelled() {
        reservationStateMachine = new ReservationStateMachine(ReservationState.CANCELLED);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            reservationStateMachine.complete();
        });

        assertEquals("Invalid reservation state", exception.getMessage());
    }

    @Test
    void cancelShouldChangeStateToCancelledWhenCurrentStateIsActive() {
        reservationStateMachine = new ReservationStateMachine(ReservationState.ACTIVE);

        reservationStateMachine.cancel();

        assertEquals(ReservationState.CANCELLED, reservationStateMachine.getCurrentState());
    }

    @Test
    void cancelShouldThrowBadRequestExceptionWhenCurrentStateIsCompleted() {
        reservationStateMachine = new ReservationStateMachine(ReservationState.COMPLETED);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            reservationStateMachine.cancel();
        });

        assertEquals("Invalid reservation state", exception.getMessage());
    }
}