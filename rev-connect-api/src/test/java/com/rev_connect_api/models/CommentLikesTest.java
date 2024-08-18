package com.rev_connect_api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentLikesTest {

    @Test
    public void testCommentLikesGettersAndSetters() {
        CommentLikes commentLikes = new CommentLikes();


        commentLikes.setCommentLikeId(1L);
        commentLikes.setUserId(100L);
        commentLikes.setCommentId(10L);
        commentLikes.setTimePosted("2024-08-15 10:00:00 AM");


        assertEquals(1L, commentLikes.getCommentLikeId());
        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());

        assertEquals("2024-08-15 10:00:00 AM", commentLikes.getTimePosted());
    }

    @Test
    public void testCommentLikesConstructor() {
        CommentLikes commentLikes = new CommentLikes(100L, 10L, "2024-08-15 10:00:00 AM");

        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());
        assertEquals("2024-08-15 10:00:00 AM", commentLikes.getTimePosted());
    }
}