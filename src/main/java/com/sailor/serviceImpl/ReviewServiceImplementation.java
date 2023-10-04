package com.sailor.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailor.entity.Product;
import com.sailor.entity.Review;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.repository.ReviewRepository;
import com.sailor.request.ReviewRequest;
import com.sailor.service.ProductService;
import com.sailor.service.ReviewService;

@Service
public class ReviewServiceImplementation  implements ReviewService {
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewRepository reviewRepo;

	@Override
	public Review createReview(ReviewRequest request, User user) throws ProductException {
		Product product = productService.findProductById(request.getProductId());
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(request.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getProductReview(Long productId) {
		
		
		return reviewRepo.getAllProductReview(productId);
	}

}
