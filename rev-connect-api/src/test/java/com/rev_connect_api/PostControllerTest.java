package com.rev_connect_api;


import com.rev_connect_api.controllers.PostController;
import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostControllerTest {

    @Autowired
    private PostController postController;

    @Test
    public void TestCreatePost() {
        PostCreateRequest post = new PostCreateRequest("test");
        ResponseEntity<Post> response = postController.CreatePost(post);

        assertEquals(post.getText(), response.getBody().getText());

        response = postController.GetPostById(1);
        assertEquals(1, response.getBody().getPostId());
    }

    @Test
    public void TestDeletePost() {
        int id = 1;
        PostCreateRequest post = new PostCreateRequest("test");
        postController.CreatePost(post);

        ResponseEntity<String> response = postController.DeletePostById(1);
        assertEquals("Successfully deleted post of id " + id, response.getBody());

        response = postController.DeletePostById(1);
        assertEquals("Post of id " + id + " does not exist in database", response.getBody());
    }

    @Test
    public void TestUpdatePost() {
        int id = 1;
        PostCreateRequest post = new PostCreateRequest("test");
        postController.CreatePost(post);

        ResponseEntity<Post> response = postController.GetPostById(id);

        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getText());

        post = new PostCreateRequest("updated");

        response = postController.UpdatePostById(post, response.getBody().getPostId());

        assertNotNull(response.getBody());
        assertEquals("updated", response.getBody().getText());
    }
}
