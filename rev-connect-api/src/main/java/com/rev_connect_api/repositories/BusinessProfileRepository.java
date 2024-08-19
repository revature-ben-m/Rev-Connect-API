package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rev_connect_api.models.BusinessProfile;
import java.util.*;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long>{

    BusinessProfile findByUserId(long userId);

    @Query(value = "SELECT u.username, u.firstname, u.lastname, u.email, u.is_business, p.* "
        + " from users u, businessprofile p "
        + " where u.id = p.user_id "
        + " and p.user_id = ?1 "
        , nativeQuery = true)
        Map<String, Object> findAllProfileInfoByUserId(long userId);
}
