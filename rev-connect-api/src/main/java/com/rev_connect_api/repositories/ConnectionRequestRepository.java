package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.recipient.id = :userId AND cr.status = 'PENDING'")
    List<ConnectionRequest> findPendingRequestsForUser(@Param("userId") Long userId);
}
