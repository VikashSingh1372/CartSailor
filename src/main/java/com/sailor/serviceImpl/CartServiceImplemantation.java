package com.sailor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.Product;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.repository.CartRepository;
import com.sailor.request.AddItemRequest;
import com.sailor.service.CartItemService;
import com.sailor.service.CartService;
import com.sailor.service.ProductService;


@Service
public class CartServiceImplemantation implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductService productService;

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepo.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
		Cart cart = cartRepo.findByUserId(userId);

		Product product = productService.findProductById(request.getProductId());
		CartItem isPresent = cartItemService.isCartitemExist(cart, product, request.getSize(), userId);

		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setCart(cart);
			cartItem.setUserId(userId);

			int price = request.getQuantity() * product.getDiscountPrice();
			cartItem.setPrice(price);
			cartItem.setSize(request.getSize());
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItem().add(createdCartItem);
		}
		return "Item Added to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);

		int totalPrice = 0;

		int totalDiscountPrice = 0;

		int totalltem = 0;

		for (CartItem cartitem : cart.getCartItem()) {

			totalPrice = totalPrice + cartitem.getPrice();
			totalDiscountPrice = totalDiscountPrice + cartitem.getDiscountPrice();

			totalltem = totalltem + cartitem.getQuantity();
		}

		cart.setTotalDiscountPrice(totalDiscountPrice);

		cart.setTotalItem(totalltem);

		cart.setTotalPrice(totalPrice);

		cart.setDiscount(totalDiscountPrice);

		return cartRepo.save(cart);
	}

}
