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

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Post> findAll()
    {
        return  postRepository.findAll();

    }
    public Optional<Post> findById(Long id)
    {
        return postRepository.findById(id);
    }
    public List<Post> findByUserAccountId(Integer accountId)
    {
        return postRepository.findByUserAccountId(accountId);
    }


    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
    @Transactional
    public void deletePostsByUserId(Integer accountId) {
        postRepository.deleteByUserAccountId(accountId);
    }
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
