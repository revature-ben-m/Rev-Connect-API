package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends  JpaRepository<User,Integer>{
  // User findByUsername(String username);
  // User findByUsernameAndEmail(String username,String email);
   User findByUsernameOrEmail(String username,String email);
    User findByResetToken(String resetToken);
    User findByUsername(String username);
    User findByEmail(String email);


}
