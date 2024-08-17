package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController {
    @Autowired
    private UserService userService;
    private PostService postService;

    public HomeController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/users")
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
        if(response == null) return ResponseEntity.status(400).body(null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable int postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    
    
    
}
