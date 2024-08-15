package com.rev_connect_api.controllers;


import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable Integer userId) {
        return postService.findByUserAccountId(userId);
    }

    @DeleteMapping("/user/{accountId}")
    public ResponseEntity<Void> deletePostByUser(@PathVariable Integer accountId) {
        postService.deletePostsByUserId(accountId);
        return ResponseEntity.noContent().build();
    }

}
