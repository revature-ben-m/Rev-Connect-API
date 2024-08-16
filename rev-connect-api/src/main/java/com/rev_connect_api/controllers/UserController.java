package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

import java.util.*;


/**
 * Controller class for handling user-related operations.
 * Provides endpoints for user registration, fetching all users, and checking if a username is taken.
 */
@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	/**
	 * Endpoint for user registration.
	 *
	 * @param firstName - the first name of the user.
	 * @param lastName - the last name of the user.
	 * @param userName - the username of the user.
	 * @param email - the email address of the user.
	 * @param password - the password for the user.
	 * @param isBusiness - a boolean indicating whether the user is a business account.
	 * @return - a {@link ResponseEntity} containing a {@link Map} with the registration result.
	 * 				Contains "success" key indicating registration success or failure, and "user" key with the
	 * 				registered user object or "message" key with an error message.
	 */
	@PostMapping(value = "/register")
	@CrossOrigin(origins = "*")
	public ResponseEntity<Map<String, Object>> register(
		@RequestParam String firstName,
		@RequestParam String lastName,
		@RequestParam String userName,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam Boolean isBusiness ){
		
		System.out.println(firstName+" "+lastName+" "+userName+" "+email+" "+password);
		User newUser = new User(userName,firstName,lastName,email,password,isBusiness);
		userService.register(newUser);

		User registeredUser = userService.checkUserId(userName);
		Map<String, Object> response = new HashMap<>();
			if (registeredUser != null) {
				response.put("success", true);
				response.put("user", registeredUser);
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "User not found!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
    }


	/**
	 * Endpoint to check if a username is already taken.
	 *
	 * @param userName - the username to check.
	 * @return - boolean value indicating whether the username is taken (true) or available(false).
	 */
	 @PostMapping(value = "/checkUserId")
	 @CrossOrigin(origins = "*")
	public Boolean checkUserId(@RequestParam String userName){
		User registeredUser = userService.checkUserId(userName);
		if(registeredUser != null) return true;
		else	return false;
	}

}
