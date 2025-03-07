package com.bosorio.ordermanagementservice.domain.business;

import com.bosorio.order_management_service.domain.business.OrderStateMachine;
import com.bosorio.order_management_service.domain.enums.OrderState;
import com.bosorio.order_management_service.domain.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderStateMachineTest {

    private OrderStateMachine orderStateMachine;

    @BeforeEach
    void setUp() {
        orderStateMachine = new OrderStateMachine(OrderState.PENDING);
    }

    @Test
    void confirmShouldChangeStateToConfirmedWhenCurrentStateIsPending() {
        orderStateMachine = new OrderStateMachine(OrderState.PENDING);

        orderStateMachine.confirm();

        assertEquals(OrderState.CONFIRMED, orderStateMachine.getOrderState());
    }

    @Test
    void confirmShouldChangeStateToConfirmedWhenCurrentStateIsConfirmed() {
        orderStateMachine = new OrderStateMachine(OrderState.CONFIRMED);

        orderStateMachine.confirm();

        assertEquals(OrderState.CONFIRMED, orderStateMachine.getOrderState());
    }

    @Test
    void confirmShouldThrowBadRequestExceptionWhenCurrentStateIsShipped() {
        orderStateMachine = new OrderStateMachine(OrderState.SHIPPED);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            orderStateMachine.confirm();
        });

        assertEquals("Invalid order state: Order in state SHIPPED cannot be marked as confirmed",
                exception.getMessage());
    }

    @Test
    void shipShouldChangeStateToShippedWhenCurrentStateIsConfirmed() {
        orderStateMachine = new OrderStateMachine(OrderState.CONFIRMED);

        orderStateMachine.ship();

        assertEquals(OrderState.SHIPPED, orderStateMachine.getOrderState());
    }

    @Test
    void shipShouldChangeStateToShippedWhenCurrentStateIsShipped() {
        orderStateMachine = new OrderStateMachine(OrderState.SHIPPED);

        orderStateMachine.ship();

        assertEquals(OrderState.SHIPPED, orderStateMachine.getOrderState());
    }

    @Test
    void shipShouldThrowBadRequestExceptionWhenCurrentStateIsPending() {
        orderStateMachine = new OrderStateMachine(OrderState.PENDING);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            orderStateMachine.ship();
        });

        assertEquals("Invalid order state: Order in state PENDING cannot be marked as shipped",
                exception.getMessage());
    }

    @Test
    void deliverShouldChangeStateToDeliveredWhenCurrentStateIsShipped() {
        orderStateMachine = new OrderStateMachine(OrderState.SHIPPED);

        orderStateMachine.deliver();

        assertEquals(OrderState.DELIVERED, orderStateMachine.getOrderState());
    }

    @Test
    void deliverShouldChangeStateToDeliveredWhenCurrentStateIsDelivered() {
        orderStateMachine = new OrderStateMachine(OrderState.DELIVERED);

        orderStateMachine.deliver();

        assertEquals(OrderState.DELIVERED, orderStateMachine.getOrderState());
    }

    @Test
    void deliverShouldThrowBadRequestExceptionWhenCurrentStateIsPending() {
        orderStateMachine = new OrderStateMachine(OrderState.PENDING);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            orderStateMachine.deliver();
        });

        assertEquals("Invalid order state: Order in state PENDING cannot be marked as delivered",
                exception.getMessage());
    }

    @Test
    void cancelShouldChangeStateToCancelledWhenCurrentStateIsPending() {
        orderStateMachine = new OrderStateMachine(OrderState.PENDING);

        orderStateMachine.cancel();

        assertEquals(OrderState.CANCELLED, orderStateMachine.getOrderState());
    }

    @Test
    void cancelShouldChangeStateToCancelledWhenCurrentStateIsConfirmed() {
        orderStateMachine = new OrderStateMachine(OrderState.CONFIRMED);

        orderStateMachine.cancel();

        assertEquals(OrderState.CANCELLED, orderStateMachine.getOrderState());
    }

    @Test
    void cancelShouldThrowBadRequestExceptionWhenCurrentStateIsDelivered() {
        orderStateMachine = new OrderStateMachine(OrderState.DELIVERED);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            orderStateMachine.cancel();
        });

        assertEquals("Invalid order state: Order in state DELIVERED cannot be marked as cancelled",
                exception.getMessage());
    }
}