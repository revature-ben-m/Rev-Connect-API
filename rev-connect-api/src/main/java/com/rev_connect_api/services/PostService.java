package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post post) {
        Post response = postRepository.save(post);
        return response;
    }

    public Post getPostById(BigInteger id) {
        Optional<Post> post = postRepository.getPostByPostId(id);
        if(post.isEmpty()) {
            return null;
        }
        return post.get();
    }

    @Transactional
    public boolean deletePostById(BigInteger id) {
        Post post = getPostById(id);
        if(post == null) {
            return false;
        }
        postRepository.deletePostByPostId(id);
        return true;
    }

    @Transactional
    public Post updatePost(Post post) {
        Post fetchedPost = getPostById(post.getPostId());
        if(fetchedPost == null) {
            return null;
        }
        fetchedPost.setUpdatedAt(getCurrentTimestamp());
        fetchedPost.setTitle(post.getTitle());
        fetchedPost.setContent(post.getContent());

        Post response = savePost(fetchedPost);
        return response;
    }

    public Post postDtoToPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }

    public Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}