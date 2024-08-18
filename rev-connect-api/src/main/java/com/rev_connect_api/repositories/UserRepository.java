package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT(:query, '%')")
    List<User> searchUsersByUsernameStartingWith(@Param("query") String query);
}
