package com.sailor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.sailor.entity.OrderItem;
import com.sailor.repository.OrderItemRepository;
import com.sailor.service.OrderItemService;

public class OrderItemServiceImplementation implements OrderItemService {
	
	
	@Autowired
	private OrderItemRepository orderItemRepo;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return orderItemRepo.save(orderItem);
	}

}
