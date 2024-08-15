package com.rev_connect_api.services;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.User;
import com.rev_connect_api.entity.RequestStatus;
import com.rev_connect_api.repositories.ConnectionRequestRepository;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserRepository userRepository;

    public ConnectionRequestService(ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository) {
        this.connectionRequestRepository = connectionRequestRepository;
        this.userRepository = userRepository;
    }

    public ConnectionRequest sendRequest(Long requesterId, Long recipientId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Requester not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        ConnectionRequest request = new ConnectionRequest();
        request.setRequester(requester);
        request.setRecipient(recipient);
        request.setStatus(RequestStatus.PENDING);
        return connectionRequestRepository.save(request);
    }

    public List<ConnectionRequest> getPendingRequestsForUser(Long userId) {
        return connectionRequestRepository.findPendingRequestsForUser(userId);
    }

    public void acceptRequest(Long requestId) {
        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus(RequestStatus.ACCEPTED);
        connectionRequestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        connectionRequestRepository.save(request);
    }
}
