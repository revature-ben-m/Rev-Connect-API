package com.rev_connect_api.services;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepo;

    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }

    public void createPost(Post post){
        postRepo.save(post);
    }
}
