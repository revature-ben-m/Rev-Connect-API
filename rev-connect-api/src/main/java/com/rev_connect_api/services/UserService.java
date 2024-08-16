package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public void register(User user){
		userRepository.save(user);
	}
	
	public User getUser(String userId){
		Optional<User> user = userRepository.findByuserId(userId);
		if(user.isPresent())
			return user.get();
		else
			return null;
	}

	public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


}
