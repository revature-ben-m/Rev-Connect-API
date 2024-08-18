package com.rev_connect_api.controllers;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;
 
@RestController
public class HomeController {
	@Autowired
    public UserService userService;
 
	@RequestMapping("/")  
    public String hello()   
    {  
        return "Hello Localhost World!!";  
    }  
 
	@PostMapping(value = "/register")
	public String register(
		@RequestParam String firstName,
		@RequestParam String lastName,
		@RequestParam String userId,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam Boolean isBusiness ){
		User newUser = new User(userId,firstName,lastName,email,password,isBusiness);
		userService.register(newUser);
 
		User registeredUser = userService.getUser(userId);
		if(registeredUser != null) return registeredUser.toString();
		else	return "User not Found!";
	}
 
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestParam String userId, @RequestParam String password) {
		User user = userService.getUser(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
 
		if (user.getPassword().equals(password)) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}
 
	// ----------------------------forgot password link---------------------------
	@PostMapping("/forgot-password")
    public String forgotPass(@RequestParam String email){
        String response = userService.forgotPass(email);
 
        return response;
    }
 
    @PutMapping("/reset-password")
    public String resetPass(@RequestParam String token, @RequestParam String password){
        return userService.resetPass(token,password);
    }
}