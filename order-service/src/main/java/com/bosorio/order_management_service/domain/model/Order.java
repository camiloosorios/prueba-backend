package com.bosorio.order_management_service.domain.model;


import com.bosorio.order_management_service.domain.business.OrderStateMachine;
import com.bosorio.order_management_service.domain.enums.OrderState;
import lombok.ToString;

@ToString
public class Order {

    private Long id;

    private Long userId;

    private OrderState status;

    private Integer version;

    public Order() {
        this.status = OrderState.PENDING;
    }

    public Order(Long id, Long userId, OrderState status, Integer version) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.version = version;
    }

    //Business Logic

    public void confirmed() {
        OrderStateMachine stateMachine = new OrderStateMachine(this.status);
        stateMachine.confirm();
        this.status = stateMachine.getOrderState();
    }

    public void ship() {
        OrderStateMachine stateMachine = new OrderStateMachine(this.status);
        stateMachine.ship();
        this.status = stateMachine.getOrderState();
    }

    public void deliver() {
        OrderStateMachine stateMachine = new OrderStateMachine(this.status);
        stateMachine.deliver();
        this.status = stateMachine.getOrderState();
    }

    public void cancel() {
        OrderStateMachine stateMachine = new OrderStateMachine(this.status);
        stateMachine.cancel();
        this.status = stateMachine.getOrderState();
    }

    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderState getStatus() {
        return status;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
