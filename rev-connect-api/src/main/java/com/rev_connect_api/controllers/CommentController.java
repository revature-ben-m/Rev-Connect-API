package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;


@RestController
public class CommentController {

    @Autowired
    CommentService commentService;


    @GetMapping("/{userId}/post/{postId}/comments")
    @CrossOrigin(origins = "*")
    public List<CommentResponse> getCommentsForPost(@PathVariable long userId, @PathVariable long postId) {
        List<Comment> commentsForPost = commentService.getCommentForPost(userId, postId);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : commentsForPost) {
            long likesCount = commentService.getLikesCountForComment(comment.getCommentId());
            responses.add(new CommentResponse(comment, likesCount));
        }
        return responses;
    }

    @PostMapping("/comments")
    @CrossOrigin(origins = "*")
    public void createComment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        LocalDateTime now = LocalDateTime.now();

        String dateTimeString = now.format(formatter);

        Comment comment = new Comment(1, 1, "text", dateTimeString);
        commentService.createComment(comment);

    }


}
