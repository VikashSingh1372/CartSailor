package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sailor.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("Select u from Cart u Where u.user.id=:userId")
	public Cart findByUserId(@Param("userId") Long userId);

}
