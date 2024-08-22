package com.rev_connect_api.services;

import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.exceptions.UnauthorizedException;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing posts in the RevConnect application.
 * Provides methods for creating, retrieving, updating, and deleting posts.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all posts in the application.
     *
     * @return a list of all {@link Post} objects.
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id the ID of the post to retrieve.
     * @return an {@link Optional} containing the post if found, or empty if not found.
     */
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    /**
     * Retrieves all posts associated with a specific user's account ID.
     *
     * @param accountId the ID of the user whose posts to retrieve.
     * @return a list of {@link Post} objects associated with the specified user ID.
     */
    public List<Post> findByUserAccountId(Integer accountId) {
        return postRepository.findByUserAccountId(accountId);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete.
     */
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    /**
     * Deletes all posts associated with a specific user's account ID.
     *
     * @param accountId the ID of the user whose posts to delete.
     */
    @Transactional
    public void deletePostsByUserId(Integer accountId) {
        postRepository.deleteByUserAccountId(accountId);
    }

    /**
     * Updates a post's title and content, ensuring that the user is authorized to update it.
     *
     * @param postId     the ID of the post to update.
     * @param userId     the ID of the user attempting to update the post.
     * @param newTitle   the new title for the post.
     * @param newContent the new content for the post.
     * @return the updated {@link Post} object.
     * @throws ResourceNotFoundException if the user or post is not found.
     * @throws UnauthorizedException     if the user is not authorized to update the post.
     */
    public Post updatePost(Long postId, Integer userId, String newTitle, String newContent) {
        // Verify if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // Retrieve the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        // Check if the post belongs to the user
        if (!post.getUser().getAccountId().equals(user.getAccountId())) {
            throw new UnauthorizedException("You are not authorized to update this post.");
        }

        // Update the post content and title
        post.setTitle(newTitle);
        post.setContent(newContent);

        // Save the updated post
        return postRepository.save(post);
    }

    /**
     * Deletes a specific post by its ID, ensuring that the user is authorized to delete it.
     *
     * @param postId the ID of the post to delete.
     * @param userId the ID of the user attempting to delete the post.
     * @throws ResourceNotFoundException if the post is not found.
     * @throws UnauthorizedException     if the user is not authorized to delete the post.
     */
    public void deletePostByUser(Long postId, Integer userId) {
        // Retrieve the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        // Check if the post belongs to the user
        if (!post.getUser().getAccountId().equals(userId)) {
            throw new UnauthorizedException("User with ID " + userId + " is not authorized to delete this post.");
        }

        // Delete the post
        postRepository.delete(post);
    }
}
