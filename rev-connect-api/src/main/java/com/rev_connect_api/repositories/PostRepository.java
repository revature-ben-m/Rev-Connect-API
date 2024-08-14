package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
