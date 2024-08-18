package com.rev_connect_api.controllers;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.models.UserDTO;
import com.rev_connect_api.services.UserService;
import com.rev_connect_api.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {
    //ddd
    @Autowired
    private UserService userService;
    
    // http://l27.0.0.1:8080/home/login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            //loginAccount check from database
            User loginAccount = userService.login(user);
            loginAccount.setPassword(null);

            // username
            String username = loginAccount.getUsername();
            Integer accountId = loginAccount.getAccountId();
            // generate token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username",username);
            claims.put("accountId",accountId);
            String token = JwtUtil.generateToken(claims);

            //DTO send to frond end
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(loginAccount.getUsername());
            userDTO.setEmail(loginAccount.getEmail());
            userDTO.setAccountId(loginAccount.getAccountId());
            userDTO.setToken(token);

            //send to front
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Object> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            return new ResponseEntity<>("请登录后再使用", HttpStatus.UNAUTHORIZED);
        }

        //check token expire time
        //return user infomation
        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
