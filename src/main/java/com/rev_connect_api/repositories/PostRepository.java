package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    
}
