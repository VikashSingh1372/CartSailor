package com.sailor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailor.entity.Cart;
import com.sailor.entity.Order;
import com.sailor.entity.User;
import com.sailor.exception.OrderException;
import com.sailor.exception.ProductException;
import com.sailor.exception.UserException;
import com.sailor.request.AddItemRequest;
import com.sailor.service.CartService;
import com.sailor.service.UserService;

@RestController
@RequestMapping("api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<String>addItemToCart(@RequestBody AddItemRequest request,
			@RequestHeader("Authorization") String jwt) throws ProductException, UserException{
		User user = userService.findUserProfileByJwt(jwt);
  cartService.addCartItem(user.getId(), request);
		
		return new ResponseEntity<>("item added to cart successfully !!!",HttpStatus.OK);
	}
	
	
	

}
