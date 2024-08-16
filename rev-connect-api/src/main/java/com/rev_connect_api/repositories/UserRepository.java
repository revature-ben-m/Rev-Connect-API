package com.rev_connect_api.repositories;

import com.rev_connect_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Integer>{
    // User findByUsername(String username);
    // User findByUsernameAndEmail(String username,String email);
    User findByUsernameOrEmail(String username,String email);
    Optional<User> findById(Integer id);
}
