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

import com.sailor.entity.Rating;
import com.sailor.entity.Review;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.exception.UserException;
import com.sailor.request.RatingRequest;
import com.sailor.request.ReviewRequest;
import com.sailor.service.RatingService;
import com.sailor.service.ReviewService;
import com.sailor.service.UserService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	

@Autowired
private UserService userService;

@Autowired
private ReviewService reviewService;

@PostMapping("/create")
public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request,
		@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
	User user = userService.findUserProfileByJwt(jwt);
	Review review = reviewService.createReview(request, user);
	return new ResponseEntity<>(review, HttpStatus.CREATED);
}

@GetMapping("/product/{productId}")
public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId,
		@RequestHeader("Authorization") String jwt) throws ProductException, UserException {
	List<Review> review = reviewService.getProductReview(productId);

	return new ResponseEntity<>(review, HttpStatus.ACCEPTED);
}
}