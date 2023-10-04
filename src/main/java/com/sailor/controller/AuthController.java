package com.sailor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailor.config.JwtProvider;
import com.sailor.entity.Cart;
import com.sailor.entity.User;
import com.sailor.exception.UserException;
import com.sailor.repository.UserRepository;
import com.sailor.request.LoginRequest;
import com.sailor.response.AuthResponse;
import com.sailor.service.CartService;
import com.sailor.service.CustomUserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private CustomUserDetails userDetail;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;
   
	private UserRepository userRepo;
	
	@Autowired
	private CartService cartService;

	public AuthController(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		User isEmailExist = this.userRepo.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new UserException("User Already exist with email:" + user.getEmail());
		}

		User createdUser = new User();
		createdUser.setFirstName(user.getFirstName());
		createdUser.setLastName(user.getLastName());
		createdUser.setEmail(user.getEmail());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepo.save(createdUser);
		Cart cart = cartService.createCart(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.gererateToken(authentication);
		AuthResponse response = new AuthResponse();
		response.setJwt(token);
		response.setMessage("Sucessfully signup");
		return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest request) throws UserException {
		Authentication authentication = authenticate(request.getUsername(), request.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.gererateToken(authentication);
		AuthResponse response = new AuthResponse();
		response.setJwt(token);
		response.setMessage("Sucessfully logged in");
		return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails user = this.userDetail.loadUserByUsername(username);

		if (user == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

}
