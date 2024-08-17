package com.rev_connect_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.services.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    
    private final UserService userService;
 
    public UserController(UserService userService, PostService postService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO registrationDTO) {
        // handle registrattion logic in UserService
        User user = userService.registerUser(mapToEntity(registrationDTO));
        UserResponseDTO responseDTO = mapToDTO(user);

        // TODO: send a verification email
        // emailService.sendVerficiationEmail(user);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@RequestParam Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateDTO) {
        User user = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
   
    // utility methods for mapping
    private User mapToEntity(@Valid UserRegistrationDTO registrationDTO) {
        // map UserRegistrationDTO to User entity
        return new User();
    }

    private UserResponseDTO mapToDTO(User user) {
        // map user entity to UserResponstDTO
        return new UserResponseDTO();
    }


    

    
    
    
}
