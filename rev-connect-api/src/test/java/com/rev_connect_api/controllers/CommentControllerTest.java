package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @InjectMocks
    public CommentController commentController;

    @Mock
    private CommentService commentService;

    @Test
    public void testGetAllCommentForPost() {
        long userId = 1L;
        long postId = 2L;

        Comment comment1 = new Comment();
        comment1.setCommentId(1L);
        Comment comment2 = new Comment();
        comment2.setCommentId(2L);

        when(commentService.getCommentForPost(userId, postId)).thenReturn(Arrays.asList(comment1, comment2));
        when(commentService.getLikesCountForComment(1L)).thenReturn(10L);
        when(commentService.getLikesCountForComment(2L)).thenReturn(5L);

        // When
        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsForPost(userId, postId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CommentResponse> body = response.getBody();
        assert body != null;
        assertEquals(2, body.size());
        assertEquals(10L, body.get(0).getLikesCount());
        assertEquals(5L, body.get(1).getLikesCount());
    }

    @Test
    public void testGetAllCommentForPostWhenNoComment() {
        long userId = 1L;
        long postId = 2L;

        when(commentService.getCommentForPost(userId, postId)).thenReturn(Collections.emptyList());
        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsForPost(userId, postId);

        List<CommentResponse> body = response.getBody();
        assert body != null;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testCreateCommentForPost() {
        Comment comment = new Comment();
        comment.setCommentId(1L);
        when(commentService.createComment(comment)).thenReturn(comment);
        ResponseEntity<Comment> response = commentController.createComment(comment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    public void testCreateCommentForCommentThrowsException() {
        Comment comment = new Comment();
        when(commentService.createComment(comment)).thenThrow(new RuntimeException());

        ResponseEntity<Comment> response = commentController.createComment(comment);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



}
