package com.bosorio.order_management_service.infrastructure.adapter.in.controller;


import com.bosorio.order_management_service.application.dto.order.CreateOrderDto;
import com.bosorio.order_management_service.application.dto.order.OrderDto;
import com.bosorio.order_management_service.application.dto.order.UpdateOrderDto;
import com.bosorio.order_management_service.application.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderDto orderDto) {
        OrderDto data = orderService.createOrder(orderDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order created successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.findAllUserOrders(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderDto orderDto) {
        OrderDto data = orderService.updateOrder(id, orderDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order updated successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
