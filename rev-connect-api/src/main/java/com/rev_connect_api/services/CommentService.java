package com.rev_connect_api.services;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
  @Autowired
  CommentRepository commentRepository;

  public List<Comment> getCommentForPost(long userId, long postId){
    return commentRepository.findByUserIdAndPostId(userId,postId);
  }

  public void createComment(Comment comment) {
    commentRepository.save(comment);
  }

}
