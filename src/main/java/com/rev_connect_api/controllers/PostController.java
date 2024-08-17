package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
