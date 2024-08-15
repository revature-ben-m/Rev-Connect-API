package com.rev_connect_api.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;



@RestController
public class CommentController {

  @Autowired
  CommentService commentService;

  @PostMapping("/comments")
  public void createComment() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

    LocalDateTime now = LocalDateTime.now();

    String dateTimeString = now.format(formatter);
      
    Comment comment = new Comment("username", "postid", "text", dateTimeString, 0 );
    commentService.createComment(comment);
      
  }
  
  
}
