package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rev_connect_api.controllers.HomeController;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.services.EmailService;
import com.rev_connect_api.services.UserService;



public class UserApiTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    public void testLoginUserNotFound() {
        String userId = "blank";
        String password = "blank";

        // Mocking the behavior of userService.getUser to return null when user is not found
        when(userService.getUser(userId)).thenReturn(null);

        ResponseEntity<?> result = homeController.login(userId, password);

        // Assert that the status is 404 NOT_FOUND
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Assert that the body contains the expected error message
        assertThat(result.getBody()).isEqualTo("User not found");
    }


    @Test
    public void testLoginInvalidCredentials() {
        // Arrange
        String userId = "user1";
        String password = "wrongwPassword";
        User user = new User(userId, "Test", "Test", "test@revature.net", "correctPassword", true);

        // Mocking the behavior of userService.getUser to return a valid user with a different password
        when(userService.getUser(userId)).thenReturn(user);
        ResponseEntity<?> result = homeController.login(userId, password);

        // Assert that the status is 401 UNAUTHORIZED
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        // Assert that the body contains the expected error message
        assertThat(result.getBody()).isEqualTo("Invalid credentials");
    }

    @Test
    public void testLoginSuccessful() {
        String userId = "user1";
        String password = "correctPassword";
        User user = new User(userId, "Test", "Test", "Test@revature.net", password, true);

        when(userService.getUser(userId)).thenReturn(user);
        ResponseEntity<?> result = homeController.login(userId, password);
        // Assert that the status is 200 OK
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Assert that the body contains the expected user object
        assertThat(result.getBody()).isEqualTo(user);
    }

    @Test
    public void testForgotPass_InvalidEmail() {
        // Arrange
        String email = "invalid@example.com";
        when(userRepository.findByuserEmail(email)).thenReturn(null);

        // Act
        String result = userService.forgotPass(email);

        // Assert
        assertEquals(null, result);
    }


}
