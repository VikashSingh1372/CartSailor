package com.sailor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sailor.entity.Cart;
import com.sailor.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("Select u from Order u Where u.user.id=:userId")
	public List<Order> getUSerOrder(@Param("userId") Long userId);
}
