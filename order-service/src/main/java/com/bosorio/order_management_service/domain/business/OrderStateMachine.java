package com.bosorio.order_management_service.domain.business;

import com.bosorio.order_management_service.domain.enums.OrderState;
import com.bosorio.order_management_service.domain.exception.BadRequestException;

public class OrderStateMachine {

    private OrderState currentState;

    public OrderStateMachine(OrderState currentState) {
        this.currentState = currentState;
    }

    public OrderState getOrderState() {
        return currentState;
    }

    public void confirm() {
        if (currentState == OrderState.PENDING || currentState == OrderState.CONFIRMED) {
            currentState = OrderState.CONFIRMED;
        } else {
            throw new BadRequestException("Invalid order state: Order in state " +
                    currentState + " cannot be marked as confirmed");
        }
    }

    public void ship() {
        if (currentState == OrderState.CONFIRMED || currentState == OrderState.SHIPPED) {
            currentState = OrderState.SHIPPED;
        } else {
            throw new BadRequestException("Invalid order state: Order in state " + currentState +
                    " cannot be marked as shipped");
        }
    }

    public void deliver() {
        if (currentState == OrderState.SHIPPED || currentState == OrderState.DELIVERED) {
            currentState = OrderState.DELIVERED;
        } else {
            throw new BadRequestException("Invalid order state: Order in state " + currentState +
                    " cannot be marked as delivered");
        }
    }

    public void cancel() {
        if (currentState == OrderState.DELIVERED) {
            throw new BadRequestException("Invalid order state: Order in state " + currentState +
                    " cannot be marked as cancelled");
        }
        currentState = OrderState.CANCELLED;
    }
}
