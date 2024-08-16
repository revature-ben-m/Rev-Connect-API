package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p WHERE p.user.accountId = :accountId")
    List<Post> findByUserAccountId(@Param("accountId") Integer accountId);
    @Transactional
    @Modifying
    @Query("DELETE  FROM Post p WHERE p.user.accountId = :accountId")
    void deleteByUserAccountId(@Param("accountId") Integer accountId);
    void deleteByIdAndUserAccountId(Long id, Integer accountId);
}