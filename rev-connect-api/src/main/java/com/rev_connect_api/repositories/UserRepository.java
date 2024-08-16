package com.rev_connect_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.User;

/**
 * This interface works as an repository and a medium between database and service to fetch information from database. It extends JPARepository so it let us use basic JPA functions without mentioning it.
 * It used the table we mention while extending it. In this case the table is User. It returns Long datatype in return if any 
 * To get customize output, It gives you specific function and structure to follow to get the expected result 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * This repository functions compare 'userName' with all other records. If userName matches with other user from database, this will return the all users information.
     * @param userName - userNmae that user enters
     * @return - returns all users with entered userName if exists
     */
    Optional<User> findByUserName(String userName);
}
