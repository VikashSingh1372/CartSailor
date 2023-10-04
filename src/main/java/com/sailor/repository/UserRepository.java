package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sailor.entity.User;

public interface UserRepository  extends  JpaRepository<User, Long> {
	
	
	public User findByEmail(String email);

}
