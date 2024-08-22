package com.rev_connect_api;

import com.rev_connect_api.controllers.PostController;
import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.exceptions.UnauthorizedException;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postService.findAll()).thenReturn(posts);

        List<Post> result = postController.getAllPosts();

        assertEquals(2, result.size());
        verify(postService, times(1)).findAll();
    }

    @Test
    void testGetPostById_Success() {
        Post post = new Post();
        when(postService.findById(1L)).thenReturn(Optional.of(post));

        ResponseEntity<Post> result = postController.getPostById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(post, result.getBody());
        verify(postService, times(1)).findById(1L);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Post> result = postController.getPostById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(postService, times(1)).findById(1L);
    }

    @Test
    void testGetPostsByUserId() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postService.findByUserAccountId(1)).thenReturn(posts);

        List<Post> result = postController.getPostsByUserId(1);

        assertEquals(2, result.size());
        verify(postService, times(1)).findByUserAccountId(1);
    }

    @Test
    void testDeletePostByUser() {
        ResponseEntity<Void> result = postController.deletePostByUser(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(postService, times(1)).deletePostsByUserId(1);
    }

    @Test
    void testUpdatePost_Success() {
        Post post = new Post();
        when(postService.updatePost(any(Long.class), any(Integer.class), any(String.class), any(String.class)))
                .thenReturn(post);

        ResponseEntity<?> result = postController.updatePost(1L, 1, "newTitle", "newContent");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(post, result.getBody());
        verify(postService, times(1)).updatePost(1L, 1, "newTitle", "newContent");
    }

    @Test
    void testUpdatePost_NotFound() {
        when(postService.updatePost(any(Long.class), any(Integer.class), any(String.class), any(String.class)))
                .thenThrow(new ResourceNotFoundException("Post not found"));

        ResponseEntity<?> result = postController.updatePost(1L, 1, "newTitle", "newContent");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(postService, times(1)).updatePost(1L, 1, "newTitle", "newContent");
    }

    @Test
    void testUpdatePost_Unauthorized() {
        when(postService.updatePost(any(Long.class), any(Integer.class), any(String.class), any(String.class)))
                .thenThrow(new UnauthorizedException("Unauthorized"));

        ResponseEntity<?> result = postController.updatePost(1L, 1, "newTitle", "newContent");

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        verify(postService, times(1)).updatePost(1L, 1, "newTitle", "newContent");
    }
}
