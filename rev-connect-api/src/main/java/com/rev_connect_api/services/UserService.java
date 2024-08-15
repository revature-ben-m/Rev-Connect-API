package com.rev_connect_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        String username=user.getUsername();
        String emailId=user.getEmail();
        List<User> checkDuplicates=getUserDetails(username,emailId);
        if(checkDuplicates.stream().anyMatch(userDetails->emailId.equals(userDetails.getEmail())))
            throw new IllegalArgumentException("Email alredy Exits");
        else if(checkDuplicates.stream().anyMatch(userDetails->username.equals(userDetails.getUsername())))
            throw new IllegalArgumentException("User Name alredy Exits");
        else
            return userRepository.saveAndFlush(user);
    }
     public List<User> getUserDetails(String userName,String email){
         List<User> users=userRepository.findByUsernameOrEmail(userName,email);
         return users;
    }
    
}
