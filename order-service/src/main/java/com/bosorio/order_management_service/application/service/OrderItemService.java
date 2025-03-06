package com.bosorio.order_management_service.application.service;

import com.bosorio.order_management_service.application.dto.orderItem.CreateOrderItemDto;
import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.application.dto.product.ProductDto;
import com.bosorio.order_management_service.application.exception.NotFoundException;
import com.bosorio.order_management_service.application.mapper.OrderItemMapper;
import com.bosorio.order_management_service.application.useCases.OrderItemUseCases;
import com.bosorio.order_management_service.domain.exception.BadRequestException;
import com.bosorio.order_management_service.domain.model.OrderItem;
import com.bosorio.order_management_service.domain.port.OrderItemPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bosorio.order_management_service.application.mapper.OrderItemMapper.fromOrderItemToOrderItemDto;


@Service
@RequiredArgsConstructor
public class OrderItemService implements OrderItemUseCases {

    private final OrderItemPersistencePort orderItemPersistencePort;

    private final ProductService productService;

    @Override
    @Transactional
    public void createOrderItem(CreateOrderItemDto orderItemDto, Long orderId) {
        ProductDto productDto = productService.getProductByIdNoCache(orderItemDto.getProductId());
        if (orderItemDto.getQuantity() > productDto.getStock()) {
            throw new BadRequestException("Insufficient stock");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setProductId(productDto.getId());
        orderItem.setOrderId(orderId);

        orderItemPersistencePort.createOrderItem(orderItem);
    }

    @Override
    public OrderItemDto findOrderItemById(Long id) {
        return orderItemPersistencePort.getOrderItemById(id)
                .map(OrderItemMapper::fromOrderItemToOrderItemDto)
                .orElseThrow(() -> new NotFoundException("Order Item not found"));
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long orderId) {
        return orderItemPersistencePort.getOrderItemsByOrderId(orderId)
                .stream()
                .map(OrderItemMapper::fromOrderItemToOrderItemDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderItemDto updateOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemPersistencePort.getOrderItemById(orderItemDto.getId())
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        ProductDto productDto = productService.getProductById(orderItemDto.getProductId());

        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setProductId(productDto.getId());

        return fromOrderItemToOrderItemDto(orderItemPersistencePort.updateOrderItem(orderItem));
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemPersistencePort.getOrderItemById(id)
                .orElseThrow(() -> new NotFoundException("Order item not found"));
        orderItemPersistencePort.deleteOrderItem(orderItem);
    }
}
