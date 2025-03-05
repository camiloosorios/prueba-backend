package com.bosorio.order_management_service.domain.model;

import lombok.ToString;

@ToString
public class OrderItem {

    private Long id;

    private Long productId;

    private Integer quantity;

    private Long orderId;

    private Integer version;

    public OrderItem() {
    }

    public OrderItem(Long id, Long productId, Integer quantity, Long orderId, Integer version) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.version = version;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
