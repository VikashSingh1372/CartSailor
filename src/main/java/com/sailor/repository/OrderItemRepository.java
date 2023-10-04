package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sailor.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
