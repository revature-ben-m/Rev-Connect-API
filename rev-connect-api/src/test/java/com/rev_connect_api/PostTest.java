package com.rev_connect_api;


import com.rev_connect_api.models.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PostTest {

    @Test
    void testPostFields() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
    }

    @Test
    void testPostWithNullValues() {
        Post post = new Post();
        post.setId(null);
        post.setTitle(null);
        post.setContent(null);

        assertNull(post.getId());
        assertNull(post.getTitle());
        assertNull(post.getContent());
    }

    @Test
    void testEqualsAndHashCode() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title");
        post1.setContent("Content");

        Post post2 = new Post();
        post2.setId(1L);
        post2.setTitle("Title");
        post2.setContent("Content");

        assertEquals(post1, post2);
        assertEquals(post1.hashCode(), post2.hashCode());
    }
}