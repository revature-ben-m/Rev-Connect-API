package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{userId}/post/{postId}/comments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CommentResponse>> getCommentsForPost(@PathVariable long userId, @PathVariable long postId) {
        List<Comment> commentsForPost = commentService.getCommentForPost(userId, postId);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : commentsForPost) {
            long likesCount = commentService.getLikesCountForComment(comment.getCommentId());
            responses.add(new CommentResponse(comment, likesCount));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/comments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createComment(@RequestBody Comment comment) {
        try {
            System.out.println(comment);
            commentService.createComment(comment);
            return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
