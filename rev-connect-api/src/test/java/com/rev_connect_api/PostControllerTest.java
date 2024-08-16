package com.rev_connect_api;


import com.rev_connect_api.controllers.PostController;
import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostControllerTest {

    @Autowired
    private PostController postController;

    @Test
    public void TestCreatePost() {
        final BigInteger id = new BigInteger("1");

        // Create post
        PostCreateRequest postRequest = new PostCreateRequest("title", "content");
        ResponseEntity<Post> response = postController.CreatePost(postRequest);
        Post postResponse = response.getBody();

        // Verifies that the controller returns a post entity similar to the post request
        assertEqualsPost(postRequest, postResponse);

        // Verifies that a post id is given to the response, it should be 1 as the entity is auto-generating
        response = postController.GetPostById(id);
        assertEquals(id, response.getBody().getPostId());
    }

    @Test
    public void TestDeletePost() {
        final BigInteger id = new BigInteger("1");

        // Creates a post
        PostCreateRequest postRequest = new PostCreateRequest("title", "content");
        postController.CreatePost(postRequest);

        // Delete a post, controller should return true if successfully deleted
        ResponseEntity<Boolean> response = postController.DeletePostById(id);
        assertEquals(true, response.getBody());

        // Try to delete the same post again, false should be returned as that post was already deleted and thus cannot be found
        response = postController.DeletePostById(id);
        assertEquals(false, response.getBody());
    }

    // This test should test both the update and get operation
    @Test
    public void TestUpdatePost() {
        final BigInteger id = new BigInteger("1");
        String title = "title test";
        String content = "content test";

        // Create post
        PostCreateRequest postRequest = new PostCreateRequest(title, content);
        postController.CreatePost(postRequest);

        // Fetches that post for comparison to ensure the get request is working
        ResponseEntity<Post> response = postController.GetPostById(id);
        Post postResponse = response.getBody();

        assertEqualsPost(postRequest, postResponse);

        // Creates the request to update the previous post
        title = "updated title";
        content = "updated content";
        postRequest = new PostCreateRequest(title, content);

        response = postController.UpdatePostById(postRequest, postResponse.getPostId());
        postResponse = response.getBody();

        // Verifies that the request is equal to the response
        assertEqualsPost(postRequest, postResponse);
        assertNotNull(postResponse.getUpdatedAt());
    }

    // Verifies if the request content of post is equal to the post from response body
    private void assertEqualsPost(PostCreateRequest postRequest, Post post) {
        assertEquals(postRequest.getTitle(), post.getTitle());
        assertEquals(postRequest.getContent(), post.getContent());
    }
}
