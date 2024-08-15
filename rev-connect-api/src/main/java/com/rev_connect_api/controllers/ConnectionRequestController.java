package com.rev_connect_api.controllers;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.services.ConnectionRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connection-requests")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;

    public ConnectionRequestController(ConnectionRequestService connectionRequestService) {
        this.connectionRequestService = connectionRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<ConnectionRequest> sendConnectionRequest(@RequestParam Long requesterId, @RequestParam Long recipientId) {
        ConnectionRequest request = connectionRequestService.sendRequest(requesterId, recipientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<ConnectionRequest>> getPendingRequests(@PathVariable Long userId) {
        List<ConnectionRequest> requests = connectionRequestService.getPendingRequestsForUser(userId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long requestId) {
        connectionRequestService.acceptRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
        connectionRequestService.rejectRequest(requestId);
        return ResponseEntity.noContent().build();
    }
}
