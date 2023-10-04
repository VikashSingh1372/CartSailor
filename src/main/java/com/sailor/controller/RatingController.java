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

import com.sailor.entity.Cart;
import com.sailor.entity.Rating;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.exception.UserException;
import com.sailor.request.AddItemRequest;
import com.sailor.request.RatingRequest;
import com.sailor.service.RatingService;
import com.sailor.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;

	@Autowired
	private RatingService ratingService;

	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest request,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		Rating rating = ratingService.createRating(request, user);
		return new ResponseEntity<>(rating, HttpStatus.CREATED);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getAllRating(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws ProductException, UserException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Rating> rating = ratingService.getProductRating(productId);

		return new ResponseEntity<>(rating, HttpStatus.OK);
	}

}
