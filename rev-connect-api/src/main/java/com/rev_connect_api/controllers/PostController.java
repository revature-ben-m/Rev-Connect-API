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

/**
 * REST controller for managing posts in the RevConnect application.
 * Provides endpoints for retrieving, updating, and deleting posts.
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Retrieves a list of all posts.
     *
     * @return a list of all {@link Post} objects.
     */
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id the ID of the post to retrieve.
     * @return a {@link ResponseEntity} containing the post if found, or a 404 Not Found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of posts by a specific user's account ID.
     *
     * @param userId the ID of the user whose posts to retrieve.
     * @return a list of {@link Post} objects associated with the specified user ID.
     */
    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable Integer userId) {
        return postService.findByUserAccountId(userId);
    }

    /**
     * Deletes all posts associated with a specific user's account ID.
     *
     * @param accountId the ID of the user whose posts to delete.
     * @return a {@link ResponseEntity} with a 204 No Content status.
     */
    @DeleteMapping("/user/{accountId}")
    public ResponseEntity<Void> deletePostByUser(@PathVariable Integer accountId) {
        postService.deletePostsByUserId(accountId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a specific post by its ID, ensuring that the user is authorized to delete it.
     *
     * @param postId the ID of the post to delete.
     * @param userId the ID of the user attempting to delete the post.
     * @return a {@link ResponseEntity} with a success message, or a 404 Not Found or 403 Forbidden status.
     */
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

    /**
     * Updates a specific post by its ID, ensuring that the user is authorized to update it.
     *
     * @param postId    the ID of the post to update.
     * @param userId    the ID of the user attempting to update the post.
     * @param newTitle  the new title for the post.
     * @param newContent the new content for the post.
     * @return a {@link ResponseEntity} with the updated post, or a 404 Not Found or 403 Forbidden status.
     */
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
