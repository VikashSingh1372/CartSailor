package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.Product;
import com.sailor.entity.Size;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	
	@Query("Select ci from CartItem ci where ci.cart=:cart And ci.product=:product And ci.userId=:userId And ci.size=:size")
public CartItem	isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
		@Param("size") String size , @Param("userId") Long userId);

}
