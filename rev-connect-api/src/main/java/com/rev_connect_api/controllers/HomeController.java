package com.rev_connect_api.controllers;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    // http://l27.0.0.1:8080/home/login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            User loginAccount = userService.login(user);
            loginAccount.setPassword(null);
            return new ResponseEntity<>(loginAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


}
