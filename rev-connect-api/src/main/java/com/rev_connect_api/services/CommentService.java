package com.rev_connect_api.services;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.repositories.CommentLikesRepository;
import com.rev_connect_api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {
  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private CommentLikesRepository commentLikesRepository;

  public void createComment(Comment comment) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
    LocalDateTime now = LocalDateTime.now();
    String dateTimeString = now.format(formatter);
    comment.setTimePosted(dateTimeString);
    commentRepository.save(comment);
  }
  public List<Comment> getCommentForPost(long userId, long postId){
    return commentRepository.findByUserIdAndPostId(userId,postId);
  }

  public boolean doesCommentExist(long commentId) {
    return commentRepository.existsByCommentId(commentId);
  }
  public long getLikesCountForComment(long commentId) {
    return commentLikesRepository.countByCommentId(commentId);
  }
}
