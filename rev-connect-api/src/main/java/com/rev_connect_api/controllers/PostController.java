package com.rev_connect_api.controllers;


import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.exceptions.UnauthorizedException;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @DeleteMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<Map<String, String>> deletePostByUser(
            @PathVariable Long postId,
            @PathVariable Integer userId
    ) {
        try {
            postService.deletePostByUser(postId, userId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Post with ID " + postId + " was successfully deleted."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Post with ID " + postId + " was not found.")
            );
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    Collections.singletonMap("message", "User with ID " + userId + " is not authorized to delete this post.")
            );
        }
    }
    @PutMapping("/user/{userId}/post/{postId}/update")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @PathVariable Integer userId,
            @RequestParam String newTitle,
            @RequestParam String newContent
    ) {
        try {
            Post updatedPost = postService.updatePost(postId, userId, newTitle, newContent);
            return ResponseEntity.ok(updatedPost);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Post with ID " + postId + " was not found."));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("message", "User with ID " + userId + " is not authorized to update this post."));
        }
    }
}
