package com.sailor.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailor.entity.Address;
import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.Order;
import com.sailor.entity.OrderItem;
import com.sailor.entity.OrderStatus;
import com.sailor.entity.User;
import com.sailor.exception.OrderException;
import com.sailor.repository.AddressRepository;
import com.sailor.repository.OrderItemRepository;
import com.sailor.repository.OrderRepository;
import com.sailor.repository.UserRepository;
import com.sailor.service.CartService;
import com.sailor.service.OrderItemService;
import com.sailor.service.OrderService;
import com.sailor.service.UserService;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private CartService cartService;

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address = addressRepo.save(shippingAddress);
		user.getAddress().add(address);
		userRepo.save(user);
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem item : cart.getCartItem()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setDiscountPrice(item.getDiscountPrice());

			OrderItem savedOrderItem = orderItemRepo.save(orderItem);

			orderItems.add(savedOrderItem);
		}

		Order order = new Order();
		order.setUser(user);
		order.setShippingAddress(address);
		order.setOrderItems(orderItems);
		order.setTotalPrice(cart.getTotalPrice());
		order.setTotalDiscountPrice(cart.getTotalDiscountPrice());
		order.setDiscount(cart.getDiscount());
		order.setTotalItem(cart.getTotalItem());
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.PENDING);
		order.getPaymentDetails().setStatus(OrderStatus.PENDING);
		order.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepo.save(order);

		// saving refrence of order in orderItem
		for (OrderItem items : orderItems) {
			items.setOrder(savedOrder);
			orderItemRepo.save(items);
		}

		return savedOrder;
	}

	@Override
	public Order findOrderByld(Long orderld) throws OrderException {
		Optional<Order> order = orderRepo.findById(orderld);
		if (order.isPresent()) {
			return order.get();
		}
		throw new OrderException("order not exist with id :" + orderld);
	}

	@Override
	public List<Order> usersOrderHistory(Long userid) {
		return orderRepo.getUSerOrder(userid);
	}

	@Override
	public Order placedOrder(Long orderld) throws OrderException {
		Order order = findOrderByld(orderld);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(OrderStatus.COMPLETED);

		return order;
	}

	@Override
	public Order confirmedOrder(Long orderld) throws OrderException {
		Order order = findOrderByld(orderld);
		order.setOrderStatus(OrderStatus.CONFIRMED);

		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Long orderld) throws OrderException {
		Order order = findOrderByld(orderld);
		order.setOrderStatus(OrderStatus.SHIPPED);

		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderld) throws OrderException {
		Order order = findOrderByld(orderld);
		order.setOrderStatus(OrderStatus.DELIVERED);

		return orderRepo.save(order);
	}

	@Override
	public Order cancledOrder(Long orderld) throws OrderException {
		Order order = findOrderByld(orderld);
		order.setOrderStatus(OrderStatus.CANCELLED);

		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}

	@Override
	public void deleteOrder(Long orderld) throws OrderException {
		// TODO Auto-generated method stub
		Order order = findOrderByld(orderld);

		orderRepo.deleteById(orderld);

	}

}
