package com.bosorio.order_management_service.infrastructure.adapter.in.client;

import com.bosorio.order_management_service.application.dto.product.ProductDto;
import com.bosorio.order_management_service.application.dto.reservation.ReservationDto;
import com.bosorio.order_management_service.domain.enums.ReservationState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "${gateway.url}")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/api/products/{id}/no-cache")
    ProductDto getProductByIdNoCache(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/api/reservations/order/{orderId}/product/{productId}")
    ReservationDto getReservation(@PathVariable Long orderId, @PathVariable Long productId, @RequestHeader("Authorization") String token);

    @PostMapping("/api/reservations")
    void createReservation(ReservationDto reservationDto, @RequestHeader("Authorization") String token);

    @PutMapping("/api/reservations/{id}")
    void updateReservation(@PathVariable Long id, @RequestBody ReservationState reservationState, @RequestHeader("Authorization") String token);

}
