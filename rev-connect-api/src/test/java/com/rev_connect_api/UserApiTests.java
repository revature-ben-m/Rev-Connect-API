package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.rev_connect_api.controllers.HomeController;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

public class UserApiTests {

    @Mock
    private UserService userService;

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

        String result = homeController.login(userId, password);
        assertThat(result).isEqualTo("User not found");
    }

    @Test
    public void testLoginInvalidCredentials() {
        // Arrange
        String userId = "user1";
        String password = "wrongwPassword";
        User user = new User(userId, "Test", "Test", "test@revature.net", "correctPassword", true);

        // Mocking the behavior of userService.getUser to return a valid user with a different password
        when(userService.getUser(userId)).thenReturn(user);
        String result = homeController.login(userId, password);

        // Assert
        assertThat(result).isEqualTo("Invalid credentials");
    }

    @Test
    public void testLoginSuccessful() {
        String userId = "user1";
        String password = "correctPassword";
        User user = new User(userId, "Test", "Test", "Test@revature.net", password, true);

        when(userService.getUser(userId)).thenReturn(user);
        String result = homeController.login(userId, password);

        assertThat(result).isEqualTo(user.toString());
    }
}
