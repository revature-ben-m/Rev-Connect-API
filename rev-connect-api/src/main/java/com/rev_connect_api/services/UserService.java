package com.rev_connect_api.services;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//

    public User  login( User user) throws Exception{
//        User dbuser= userRepository.findByUsername(user.getUsername());
        User dbuser= userRepository.findByUsernameOrEmail(user.getUsername(),user.getUsername());
        if(dbuser == null || !user.getPassword().equals(dbuser.getPassword())){
         throw new IllegalArgumentException("password invalid");
        }
 
        return dbuser ;
      }
}
