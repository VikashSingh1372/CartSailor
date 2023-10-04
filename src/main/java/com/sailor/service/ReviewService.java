package com.sailor.service;

import java.util.List;

import com.sailor.entity.Review;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.request.ReviewRequest;

public interface ReviewService {
	
	
	public Review createReview(ReviewRequest request,User user) throws ProductException;
	public List<Review> getProductReview(Long productId);

}
