package com.rev_connect_api.repositories;



import com.rev_connect_api.models.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Post} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Retrieves all posts associated with a specific user's account ID.
     *
     * @param accountId the ID of the user whose posts to retrieve.
     * @return a list of {@link Post} objects associated with the specified user ID.
     */
    @Query("SELECT p FROM Post p WHERE p.user.accountId = :accountId")
    List<Post> findByUserAccountId(@Param("accountId") Integer accountId);

    /**
     * Deletes all posts associated with a specific user's account ID.
     * This operation is transactional and modifying, meaning it performs a bulk delete operation.
     *
     * @param accountId the ID of the user whose posts to delete.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Post p WHERE p.user.accountId = :accountId")
    void deleteByUserAccountId(@Param("accountId") Integer accountId);

    /**
     * Deletes a specific post by its ID and the associated user's account ID.
     * This method is automatically provided by Spring Data JPA based on the method name.
     *
     * @param id        the ID of the post to delete.
     * @param accountId the ID of the user whose post to delete.
     */
    void deleteByIdAndUserAccountId(Long id, Integer accountId);
}
