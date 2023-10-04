package com.sailor.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ordered_id")
	private Long orderId;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	private LocalDateTime orderDate;
	
	private LocalDateTime deliverDate;

	@OneToOne
	private Address shippingAddress;

	@Embedded
	private PaymentDetails paymentDetails = new PaymentDetails();

	private double totalPrice;
	
	private Integer totalDiscountPrice;
	
	private Integer discount;
	
	private OrderStatus orderStatus;
	
	private int totalItem;
	
	
	private LocalDateTime createdAt;

}
