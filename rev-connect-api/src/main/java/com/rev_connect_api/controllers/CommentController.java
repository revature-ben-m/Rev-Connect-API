package com.rev_connect_api.controllers;

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
    public List<Comment> getCommentsForPost(@PathVariable long userId, @PathVariable long postId) {
        List<Comment> commentsForPost = commentService.getCommentForPost(userId, postId);
        return commentsForPost;
    }

    @PostMapping("/comments")
    public void createComment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        LocalDateTime now = LocalDateTime.now();

        String dateTimeString = now.format(formatter);

        Comment comment = new Comment(1, 1, "text", dateTimeString);
        commentService.createComment(comment);

    }


}
