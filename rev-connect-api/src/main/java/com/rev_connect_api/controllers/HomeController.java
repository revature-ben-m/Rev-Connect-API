package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

@RestController
public class HomeController {
	
	@Autowired
	UserService userService;

	@RequestMapping("/")  
    public String hello()   
    {  
        return "Hello Localhost World!!";  
    }  

	@PostMapping(value = "/register")
	public String register(
		@RequestParam String firstName,
		@RequestParam String lastName,
		@RequestParam String userName,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam Boolean isBusiness ){
		
		User newUser = new User(userName,firstName,lastName,email,password,isBusiness);
		userService.register(newUser);

		User registeredUser = userService.getUser(userName);
		if(registeredUser != null) return registeredUser.toString();
		else	return "User not Found!";
	 }

	 @PostMapping(value = "/checkUserId")
	public Boolean checkUserId(@RequestParam String userId){
		User registeredUser = userService.getUser(userId);
		if(registeredUser != null) return true;
		else	return false;
	}

}
