package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    // The directory to upload media
    private final String attachmentsDirectory = "../attachments";
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> CreatePost(@RequestBody PostCreateRequest postCreateRequest) {
        // Convert request to post entity
        Post post = postService.postDtoToPost(postCreateRequest);
        post.setCreatedAt(postService.getCurrentTimestamp());
        // Call service to save it
        Post response = postService.savePost(post);
        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
//                                           @RequestParam("content") String content,
//                                           @RequestParam(value = "file", required = false) MultipartFile file) {
//
//        System.out.println("Title: " + title);
//        System.out.println("Content: " + content);
//        System.out.println("File: " + file);
//        if(file != null) {
//            System.out.println("File content: " + file.getContentType());
//        }
//
//        return null;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPostById(@PathVariable BigInteger id) {
        Post response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> GetRecentPosts(@RequestParam int page) {
        List<Post> posts = postService.getRecentPosts(page);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeletePostById(@PathVariable BigInteger id) {
        boolean deleted = postService.deletePostById(id);
        return ResponseEntity.ok(deleted);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> UpdatePostById(@RequestBody @Valid PostCreateRequest postCreateRequest,
                                               @PathVariable BigInteger id) {
        Post post = postService.postDtoToPost(postCreateRequest);
        post.setPostId(id);
        post = postService.updatePost(post);
        return ResponseEntity.ok(post);
    }
}