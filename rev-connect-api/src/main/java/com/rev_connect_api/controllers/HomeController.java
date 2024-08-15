package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

import java.io.Console;
import java.util.*;

@RestController
public class HomeController {
	
	@Autowired
	UserService userService;

	@RequestMapping("/")  
    public String hello()   
    {  
        return "Hello Localhost World!!";  
    }  

	@GetMapping("/users")
	@CrossOrigin(origins = "*")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}

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

		User registeredUser = userService.getUser(userName);
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

	 @PostMapping(value = "/checkUserId")
	 @CrossOrigin(origins = "*")
	public Boolean checkUserId(@RequestParam String userName){
		User registeredUser = userService.getUser(userName);
		if(registeredUser != null) return true;
		else	return false;
	}

}
