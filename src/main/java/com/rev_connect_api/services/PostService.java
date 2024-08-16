package com.rev_connect_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    //Checks conditions for allowing a post, and persists to database
    public Post createPost(Post post) {
        //possibly add authentication check here
        if (post.getPostText().length() <= 255 &&
            post.getPostText() != "" &&
            userRepository.existsById(post.getPostedBy())) {
            return postRepository.save(post);
        }
        else return null;
    }

    public Post getPostById(int postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
