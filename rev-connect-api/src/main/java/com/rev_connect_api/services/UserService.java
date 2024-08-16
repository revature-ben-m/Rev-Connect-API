package com.rev_connect_api.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

/**
 * This class serves a sevice between API call and repository. This class maintains directly by spring boot becuase of annotation. 
 */
@Service
public class UserService {

	/**
	 * AutoWired User Repository by Spring boot
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * This function register user into databse with values passed by controller
	 * @param user - user information to be stored into database
	 */
	public void register(User user){
		userRepository.save(user);
	}
	
	/**
	 * This function checks wheather the userName is entering by user is already taken by another user
	 * @param userName- UserName enters by user
	 * @return - Returns boolean values true if userName is already taken and false if userName is not taken
	 */
	public User checkUserId(String userName){
		Optional<User> user = userRepository.findByUserName(userName);
		if(user.isPresent())
			return user.get();
		else
			return null;
	}
}
