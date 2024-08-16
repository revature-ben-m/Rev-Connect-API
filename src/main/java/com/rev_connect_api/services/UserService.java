package com.rev_connect_api.services;

import org.springframework.stereotype.Service;

import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
}
