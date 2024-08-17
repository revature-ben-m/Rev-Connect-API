package com.rev_connect_api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.dto.LoginRequestDTO;
import com.rev_connect_api.security.JWTAuthenticationFilter;
import com.rev_connect_api.services.AuthService;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginDTO) {
        try {

            logger.info("\n\nThis is the login in response: {}\n\n", loginDTO.toString());
            // authenticate the user and generate a jwt token
            String jwtToken = authService.loginUser(loginDTO);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            System.out.println("Error during login -- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        
    }
}
