package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger> {

    Optional<Post> getPostByPostId(BigInteger id);
    void deletePostByPostId(BigInteger id);
    
}
