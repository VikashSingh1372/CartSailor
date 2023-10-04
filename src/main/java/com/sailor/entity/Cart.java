package com.sailor.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor 
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cart", orphanRemoval = true)
	private  List<CartItem>  cartItem = new ArrayList<>();
	
	private double totalPrice;
	
	private int totalItem;
	
	private int totalDiscountPrice;
	
	private int discount;

}
