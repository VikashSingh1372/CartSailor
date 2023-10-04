package com.sailor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailor.entity.Rating;
import com.sailor.entity.User;
import com.sailor.exception.ProductException;
import com.sailor.exception.UserException;
import com.sailor.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt)
			throws ProductException, UserException {
		User user = userService.findUserProfileByJwt(jwt);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
