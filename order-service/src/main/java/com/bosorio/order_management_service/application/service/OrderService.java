package com.bosorio.order_management_service.application.service;

import com.bosorio.order_management_service.application.dto.events.ReservationEvent;
import com.bosorio.order_management_service.application.dto.order.CreateOrderDto;
import com.bosorio.order_management_service.application.dto.order.OrderDto;
import com.bosorio.order_management_service.application.dto.order.UpdateOrderDto;
import com.bosorio.order_management_service.application.dto.orderItem.OrderItemDto;
import com.bosorio.order_management_service.application.dto.product.ProductDto;
import com.bosorio.order_management_service.application.dto.reservation.ReservationDto;
import com.bosorio.order_management_service.application.dto.user.UserDto;
import com.bosorio.order_management_service.application.enums.ReservationEventType;
import com.bosorio.order_management_service.application.exception.NotFoundException;
import com.bosorio.order_management_service.application.mapper.OrderMapper;
import com.bosorio.order_management_service.application.useCases.OrderUseCases;
import com.bosorio.order_management_service.domain.enums.OrderState;
import com.bosorio.order_management_service.domain.enums.ReservationState;
import com.bosorio.order_management_service.domain.model.Order;
import com.bosorio.order_management_service.domain.port.OrderPersistencePort;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.OrderEventProducer;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.ProductService;
import com.bosorio.order_management_service.infrastructure.adapter.out.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bosorio.order_management_service.application.mapper.OrderMapper.fromOrderToOrderDto;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCases {

    private final OrderPersistencePort orderPersistencePort;

    private final UserService userService;

    private final OrderItemService orderItemService;

    private final ProductService productService;

    private final OrderEventProducer orderEventProducer;

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderDto orderDto) {
        System.out.println("entro");
        UserDto userDto = userService.getUserById(orderDto.getUserId());
        System.out.println("salio");

        Order order = new Order();
        order.setUserId(userDto.getId());

        Order savedOrder = orderPersistencePort.createOrder(order);

        orderDto.getOrderItems()
                .forEach(orderItemDto -> orderItemService.createOrderItem(orderItemDto, savedOrder.getId()));

        return fromOrderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto findOrderById(Long id) {
        return orderPersistencePort.findOrderById(id)
                .map(OrderMapper::fromOrderToOrderDto)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> findAllUserOrders(Long userId) {
        return orderPersistencePort.findAllUserOrders(userId)
                .stream()
                .map(OrderMapper::fromOrderToOrderDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long id, UpdateOrderDto orderDto) {
        Order order = orderPersistencePort.findOrderById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        switch (orderDto.getOrderState()) {
            case CONFIRMED -> order.confirmed();
            case SHIPPED -> order.ship();
            case DELIVERED -> order.deliver();
            case CANCELLED -> order.cancel();
        }

        order.setUserId(orderDto.getUserId());
        order.setVersion(orderDto.getVersion());

        List<OrderItemDto> orderItemsDto = orderItemService.findOrderItemsByOrderId(order.getId());

        orderItemsDto.forEach(orderItemDto -> {
            ProductDto productDto = productService.getProductById(orderItemDto.getProductId());
            if (order.getStatus() == OrderState.CONFIRMED) {
                ReservationDto reservationExist = productService.getReservation(order.getId(), productDto.getId());

                if (reservationExist == null || reservationExist.getStatus() == ReservationState.CANCELLED) {
                    ReservationEvent reservationEvent = new ReservationEvent(
                            null,
                            order.getId(),
                            productDto.getId(),
                            orderItemDto.getQuantity(),
                            ReservationEventType.CREATE
                    );
                    orderEventProducer.sendReservationEvent(reservationEvent);
                }
            }
            if (order.getStatus() == OrderState.CANCELLED) {
                ReservationDto reservationExist = productService.getReservation(order.getId(), productDto.getId());
                if (reservationExist != null) {
                    ReservationEvent reservationEvent = new ReservationEvent(
                            reservationExist.getId(),
                            order.getId(),
                            productDto.getId(),
                            orderItemDto.getQuantity(),
                            ReservationEventType.CANCEL
                    );
                    orderEventProducer.sendReservationEvent(reservationEvent);
                }
            }
        });

        return fromOrderToOrderDto(orderPersistencePort.updateOrder(order));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderPersistencePort.findOrderById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        orderPersistencePort.deleteOrder(order);
    }
}
