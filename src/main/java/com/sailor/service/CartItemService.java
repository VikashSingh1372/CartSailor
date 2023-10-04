package com.sailor.service;

import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.Product;
import com.sailor.exception.CartItemException;
import com.sailor.exception.UserException;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);

	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartitemExist(Cart cart, Product product, String size, Long userid);

	public void removeCartitem(Long userid, Long cartitemid) throws CartItemException, UserException;

	public CartItem findCartitemByld(Long cartitemid) throws CartItemException;

}
