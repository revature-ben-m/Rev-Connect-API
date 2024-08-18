package com.rev_connect_api.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);    
    private final long EXPIRE_TOKEN = 15; // Expiration time in minutes

    // Generate a JWT token for password reset
    public String generateResetToken(String email) {
        long expirationTime = EXPIRE_TOKEN * 60 * 1000; // 15 minutes expiration

        return Jwts.builder()
        	.setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact();
    }

    // Register a new user
    public void register(User user){
        userRepository.save(user);
    }
    
    // Get a user by userId
    public User getUser(String userId){
        Optional<User> user = userRepository.findByuserId(userId);
        return user.orElse(null);
    }

    // Get a user by email
    public User getUserByEmail(String email){
        return userRepository.findByuserEmail(email);
    }

    // Handle forgot password request
    public String forgotPass(String email) {
        // Find the user by email
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByuserEmail(email));

        if (userOptional.isEmpty()) {
            return "Invalid email id.";
        }

        // Generate reset token
        String resetToken = generateResetToken(email);
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;

        // Send reset link to the provided email address
        emailService.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetLink);

        return "Password reset link has been sent to your email.";
    }

    // Handle password reset request
    public String resetPass(String token, String newPassword) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String email = claims.getSubject();

            User user = userRepository.findByuserEmail(email);
            if (user == null) {
                return "Invalid token.";
            }

            // Update the user's password (ensure to hash the password before saving)
            user.setPassword(newPassword);
            userRepository.save(user);

            return "Your password has been successfully updated.";
        } catch (JwtException e) {
            return "Invalid or expired token.";
        }
    }
}
