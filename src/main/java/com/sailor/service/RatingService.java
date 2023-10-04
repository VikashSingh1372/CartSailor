package com.sailor.service;

import java.util.List;

import com.sailor.entity.Rating;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.exception.UserException;
import com.sailor.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest request, User user) throws ProductException;
	public List<Rating> getProductRating(Long productId) throws ProductException;

}
