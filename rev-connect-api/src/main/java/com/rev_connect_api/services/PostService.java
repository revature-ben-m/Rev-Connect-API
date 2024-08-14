package com.rev_connect_api.services;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post post) {
        Post response = postRepository.save(post);
        return post;
    }

    public Post getPostById(int id) {
        Optional<Post> post = postRepository.getPostByPostId(id);
        if(post.isEmpty()) {
            return null;
        }
        return post.get();
    }
}
