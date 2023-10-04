package com.sailor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sailor.entity.User;
import com.sailor.repository.UserRepository;

@Service
public class CustomUserDetails   implements UserDetailsService  {
	
	private UserRepository userRepo;

	public CustomUserDetails(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  User user = this.userRepo.findByEmail(username);
		  
		  
		  if(user == null) {
			  throw new UsernameNotFoundException("User do not exist with email id :  !!!!" +username ) ;
		  }
		  List<GrantedAuthority> authorities =  new   ArrayList<GrantedAuthority>();
		
		return new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
