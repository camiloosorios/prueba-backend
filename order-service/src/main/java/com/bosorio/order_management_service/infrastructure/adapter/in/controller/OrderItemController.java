package com.bosorio.order_management_service.infrastructure.adapter.in.controller;

import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.application.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public OrderItemDto getOrderItemById(@PathVariable Long id) {
        return orderItemService.findOrderItemById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.findOrderItemsByOrderId(orderId);
    }

    @PatchMapping
    public ResponseEntity<?> updateOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        OrderItemDto data = orderItemService.updateOrderItem(orderItemDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order item updated successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order item deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
