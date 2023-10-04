package com.sailor.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailor.entity.Product;
import com.sailor.entity.Rating;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.repository.RatingRepository;
import com.sailor.request.RatingRequest;
import com.sailor.service.ProductService;
import com.sailor.service.RatingService;

@Service
public class RatingServiceImplementation implements RatingService {

	@Autowired
	private ProductService productService;

	@Autowired
	private RatingRepository ratingRepo;

	@Override
	public Rating createRating(RatingRequest request, User user) throws ProductException {
		Product product = productService.findProductById(request.getProductId());
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setCreatedAt(LocalDateTime.now());
		rating.setRating(request.getRating());
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) throws ProductException {
		
		
		return   ratingRepo.getAllProductRating(productId);
	}

}
