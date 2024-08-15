package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.BusinessProfile;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long>{
    @Query("SELECT a FROM BusinessProfile a WHERE a.userId = ?1")
    BusinessProfile findBusinessProfileByUserId(Long userId);
}
