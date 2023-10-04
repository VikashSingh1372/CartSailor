package com.sailor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailor.entity.Address;
import com.sailor.entity.Order;
import com.sailor.entity.User;
import com.sailor.exception.OrderException;
import com.sailor.exception.UserException;
import com.sailor.service.OrderService;
import com.sailor.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrder(@RequestBody Address shippingAdress,
			@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.createOrder(user, shippingAdress);
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity <List<Order>>userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);

		List<Order> order = orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}
	@GetMapping("/{orderId}")
	public ResponseEntity <Order>userOrderHistory( @PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.findOrderByld(orderId);
		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}

}
