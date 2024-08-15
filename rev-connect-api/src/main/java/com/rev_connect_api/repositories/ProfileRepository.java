package com.rev_connect_api.repositories;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.User;

@Repository
public interface ProfileRepository extends JpaRepository<PersonalProfile, Long> {
  Optional<PersonalProfile> findByUser(User user);
  Optional<PersonalProfile> findByUser_UId(Long userId);
    
}
