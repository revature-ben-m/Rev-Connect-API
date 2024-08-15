package com.rev_connect_api.services;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  @Autowired
  CommentRepository commentRepository;
  public void createComment(Comment comment) {

    commentRepository.save(comment);
  }
}
