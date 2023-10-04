package com.sailor.service;

import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest request) throws ProductException;

	public Cart findUserCart(Long userId);
}
