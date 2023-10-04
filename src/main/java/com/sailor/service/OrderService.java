package com.sailor.service;

import java.util.List;

import com.sailor.entity.Address;
import com.sailor.entity.Order;
import com.sailor.entity.User;
import com.sailor.exception.OrderException;

public interface OrderService {
	
	public Order createOrder(User user,Address shippingAddress);

	public Order findOrderByld(Long orderld) throws  OrderException;

	public List<Order> usersOrderHistory(Long userid);

	public Order placedOrder(Long orderld) throws OrderException;

	public Order confirmedOrder(Long orderld) throws OrderException;

	public Order shippedOrder(Long orderld) throws OrderException;

	public Order deliveredOrder(Long orderld) throws OrderException;

	public Order cancledOrder(Long orderld) throws OrderException;

	public List<Order> getAllOrders();

	public void deleteOrder(Long orderld) throws OrderException;

}
