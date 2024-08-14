package com.rev_connect_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.services.UserService;

@RestController
public class HomeController {
    @Autowired
    private UserService userService;
    private PostService postService;

    public HomeController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println("*************************Startig point*****************");
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Person created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post response = postService.createPost(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable int postId) {
        return ResponseEntity.ok(postService.getMessageById());
    }
    
    
    
}
