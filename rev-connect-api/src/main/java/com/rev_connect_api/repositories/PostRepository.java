package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> getPostByPostId(int id);

    void deletePostByPostId(int id);
}
