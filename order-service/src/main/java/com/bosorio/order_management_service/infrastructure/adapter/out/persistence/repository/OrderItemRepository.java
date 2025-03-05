package com.bosorio.order_management_service.infrastructure.adapter.out.persistence.repository;

import com.bosorio.order_management_service.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByOrderId(Long orderId);

}
