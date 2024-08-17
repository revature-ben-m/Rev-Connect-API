package com.rev_connect_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    public List<User> findByUsernameOrEmail(String userName,String email);

    public User findByUsername(String username); 
}
