package com.bosorio.product_service.infrastructure.adapter.in.controller;

import com.bosorio.product_service.application.dto.ReservationDto;
import com.bosorio.product_service.application.service.ReservationService;
import com.bosorio.product_service.domain.enums.ReservationState;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto data = reservationService.createReservation(reservationDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reservation created successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/order/{orderId}")
    public List<ReservationDto> getReservationsByOrderIdAndProductId(@PathVariable Long orderId) {
        return reservationService.getReservationsByOrderId(orderId);
    }

    @GetMapping("/order/{orderId}/product/{productId}")
    public ReservationDto getReservationsByOrderIdAndProductId(@PathVariable Long orderId, @PathVariable Long productId) {
        return reservationService.getReservationByOrderIdAndProductId(orderId, productId);
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservation(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable("id") Long id,@RequestBody ReservationState reservationState) {
        ReservationDto data = reservationService.updateReservation(id, reservationState);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reservation updated successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reservation deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
