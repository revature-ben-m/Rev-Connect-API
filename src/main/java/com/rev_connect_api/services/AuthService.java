package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.dto.LoginRequestDTO;
import com.rev_connect_api.models.User;

@Service
public class AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Authenticate user and generate a JWT token
    public String loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userService.findUserByUsername(loginRequestDTO.getUsername());
        if (user != null && passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())) {
            // TODO: generate JWT token
            return generateToken(user);
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    // placeholder method for generating JWT tokens
    private String generateToken(User user) {
        // TODO: JWT token generation logic
        return "JWT_TOKEN_PLACEHOLDER";
    }
}
