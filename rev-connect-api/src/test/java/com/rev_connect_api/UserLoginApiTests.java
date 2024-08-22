package com.rev_connect_api;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.rev_connect_api.models.UserDTO;

import com.rev_connect_api.controllers.HomeController;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserLoginApiTests {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private HomeController homeControllerUnderTest;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    //Tests successful login with valid username and password
    @Test
    public void testLoginIsSuccessful() throws Exception {

        User loginRequest = new User();
        loginRequest.setUsername("Ram");
        loginRequest.setPassword("Ram123");

        User authUser = new User();
        authUser.setUsername("Ram");
        authUser.setPassword("Ram123");

        // Create UserDTO for response
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(authUser.getUsername());
        userDTO.setEmail("email@example.com");
        userDTO.setAccountId(1);
        userDTO.setToken("token");

        when(mockUserService.login(loginRequest)).thenReturn(authUser);
        ResponseEntity<Object> response = homeControllerUnderTest.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO returnedUserDTO = (UserDTO) response.getBody();
        assertEquals("Ram", returnedUserDTO.getUsername());
        assertEquals(null, authUser.getPassword());
    }

    // Tests that login fails with an invalid password
    @Test
    public void testLoginWithInvalidPassword() throws Exception {
        // Arrange
        User User2 = new User();
        User2.setUsername("Dilip");
        User2.setPassword("wrongpassword");

        when(mockUserService.login(User2)).thenThrow(new IllegalArgumentException("password invalid"));


        ResponseEntity<Object> response = homeControllerUnderTest.login(User2);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("password invalid", response.getBody());
    }

    // Tests that login fails when the user does not exist
    @Test
    public void testLoginWithNoExistentUser() throws Exception {
        // Arrange
        User User3 = new User();
        User3.setUsername("noUser");
        User3.setPassword("noPassword");

        when(mockUserService.login(User3)).thenThrow(new IllegalArgumentException("password invalid"));


        ResponseEntity<Object> response = homeControllerUnderTest.login(User3);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("password invalid", response.getBody());
    }
}
