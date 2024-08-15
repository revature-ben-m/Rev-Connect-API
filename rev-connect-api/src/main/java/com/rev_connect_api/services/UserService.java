package com.rev_connect_api.services;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(User user) throws Exception {
        // Fetch the user from the repository based on username or email
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getUsername());

        // Check if user is present and password matches
        if (optionalUser.isEmpty() || !user.getPassword().equals(optionalUser.get().getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Return the user with sensitive information removed
        User dbUser = optionalUser.get();
        dbUser.setPassword(null); // Do not expose password
        return dbUser;
    }

    public User getUserById(Long userId) {
        // Fetch user by ID from the repository
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
