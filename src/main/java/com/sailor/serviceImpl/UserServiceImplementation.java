package com.sailor.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailor.config.JwtProvider;
import com.sailor.entity.User;
import com.sailor.exception.UserException;
import com.sailor.repository.UserRepository;
import com.sailor.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserById(long userId) throws UserException {
		  Optional<User> user = this.userRepo.findById(userId);
		  if(user.isPresent()) {
			  return user.get();
		  }
		  else {
			   throw new UserException("user not found with id :"+userId);
		  }
	
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email = jwtProvider.getEmailFromToken(jwt);
		 User user = userRepo.findByEmail(email);
		 if(user == null) {
			 throw new UserException("user not exist");
		 }
		return user;
	}

}
