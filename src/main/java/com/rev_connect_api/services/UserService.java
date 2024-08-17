package com.rev_connect_api.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        String username=user.getUsername();
        String emailId=user.getEmail();
        List<User> checkDuplicates=getUserDetails(username,emailId);
        // hashing password then persisting hashed password to the database
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if(checkDuplicates.stream().anyMatch(userDetails->emailId.equals(userDetails.getEmail())))
            throw new IllegalArgumentException("Email already exists");

        if(checkDuplicates.stream().anyMatch(userDetails->username.equals(userDetails.getUsername())))
            throw new IllegalArgumentException("Username already exists");

        return userRepository.saveAndFlush(user);
    }

    public boolean authenticateUser(String username, String plainPassword) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return passwordEncoder.matches(plainPassword, user.getPassword());
        }
        return false;
    }

     public List<User> getUserDetails(String userName,String email){
         List<User> users=userRepository.findByUsernameOrEmail(userName,email);
         return users;
    }
}
