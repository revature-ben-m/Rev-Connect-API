package com.rev_connect_api.controllers;

import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class CommentLikesController {
    @Autowired
    private CommentLikesService commentLikesService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{commentId}/like/{userId}")
    @CrossOrigin(origins = "*")
    public void likeComment(@PathVariable long userId, @PathVariable long commentId) {

        if (commentService.doesCommentExist(commentId)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            String dateTimeString = now.format(formatter);

            CommentLikes like = new CommentLikes(userId, commentId, dateTimeString);
            commentLikesService.like(like);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @PostMapping("/comment/{commentId}/unlike/{userId}")
    @CrossOrigin(origins = "*")
    public void unlikeComment(@PathVariable long userId, @PathVariable long commentId) {
        if (commentService.doesCommentExist(commentId)) {
            commentLikesService.unlike(userId, commentId);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
