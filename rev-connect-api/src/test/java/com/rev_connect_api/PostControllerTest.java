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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostControllerTest {

    @Autowired
    private PostController postController;

    @Test
    @DirtiesContext // Ensures each test do not share state or data with each other
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
    @DirtiesContext
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

    @Test
    @DirtiesContext
    public void TestGetRecentPosts() {
        // initialPostCount should be equal to MAX_POST_PER_PAGE from PostService.java, test will most likely fail if not
        final int initialPostCount = 5;
        final int remainderPostCount = 3;

        final String title = "some titles";
        final String content = "some contents";

        for(int i = 0; i < initialPostCount; i++) {
            PostCreateRequest postRequest = new PostCreateRequest(title, content);
            postController.CreatePost(postRequest);
        }

        ResponseEntity<List<Post>> postsResponse = postController.GetRecentPosts(0);
        // Should return the most recent post sorted from most recent to least recent
        List<Post> posts = postsResponse.getBody();
        // Checks to make sure there 5 posts are returned
        assertEquals(initialPostCount, posts.size());
        // Loop through each one to verify the data is correct
        Post previousPost = posts.get(0);
        posts.forEach(post -> {
            assertEquals(title, post.getTitle());
            assertEquals(content, post.getContent());
            // The previous post(the more recent) should have a timestamp greater than or equal to the current post, this ensures the posts are sorted by most recent
            boolean createdAtAfterOrAtSameTime = (previousPost.getCreatedAt().after(post.getCreatedAt()))
                    || previousPost.getCreatedAt().equals(post.getCreatedAt());
            assertTrue(createdAtAfterOrAtSameTime);
        });

        // After the first posts creation, create more posts by remainderPostCount, then fetch the next page (1) and verify that it is equal to remainderPostCount
        for(int i = 0; i < remainderPostCount; i++) {
            PostCreateRequest postRequest = new PostCreateRequest(title, content);
            postController.CreatePost(postRequest);
        }
        // Gets the next page, should verify if pagination is working
        postsResponse = postController.GetRecentPosts(1);
        posts = postsResponse.getBody();
        assertEquals(remainderPostCount, posts.size());
    }

    // Verifies if the request content of post is equal to the post from response body
    private void assertEqualsPost(PostCreateRequest postRequest, Post post) {
        assertEquals(postRequest.getTitle(), post.getTitle());
        assertEquals(postRequest.getContent(), post.getContent());
    }
}
