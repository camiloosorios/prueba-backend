package com.bosorio.order_management_service.application.service;

import com.bosorio.order_management_service.application.dto.product.ProductDto;
import com.bosorio.order_management_service.application.dto.reservation.ReservationDto;
import com.bosorio.order_management_service.domain.enums.ReservationState;
import com.bosorio.order_management_service.infrastructure.adapter.in.client.ProductClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;

    private final HttpServletRequest request;

    public ProductDto getProductById(Long id) {
        String token = request.getHeader("Authorization");
        return productClient.getProductById(id, token);
    }

    public ProductDto getProductByIdNoCache(Long id) {
        String token = request.getHeader("Authorization");
        return productClient.getProductByIdNoCache(id, token);
    }

    public ReservationDto getReservation(Long orderId, Long productId) {
        String token = request.getHeader("Authorization");
        return productClient.getReservation(orderId, productId, token);
    }

    public void createReservation(ReservationDto reservationDto) {
        String token = request.getHeader("Authorization");
        productClient.createReservation(reservationDto, token);
    }

    public void updateReservation(Long id, ReservationState status) {
        String token = request.getHeader("Authorization");
        productClient.updateReservation(id, status, token);
    }

}
