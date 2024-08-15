package com.rev_connect_api.services;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
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
}
