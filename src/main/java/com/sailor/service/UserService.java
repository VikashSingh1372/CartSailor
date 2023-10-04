package com.sailor.service;

import com.sailor.entity.User;
import com.sailor.exception.UserException;

public interface UserService {
	

	public User findUserById(long userId) throws UserException;
	public User findUserProfileByJwt(String jwt)throws UserException;

}
