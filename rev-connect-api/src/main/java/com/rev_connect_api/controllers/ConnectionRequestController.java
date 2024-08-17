package com.rev_connect_api.controllers;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.services.ConnectionRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connect")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;

    public ConnectionRequestController(ConnectionRequestService connectionRequestService) {
        this.connectionRequestService = connectionRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<ConnectionRequest> sendRequest(@RequestParam Long requesterId, @RequestParam Long recipientId) {
        try {
            ConnectionRequest request = connectionRequestService.sendRequest(requesterId, recipientId);
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<ConnectionRequest>> getPendingRequestsForUser(@PathVariable Long userId) {
        List<ConnectionRequest> pendingRequests = connectionRequestService.getPendingRequestsForUser(userId);
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long requestId) {
        try {
            connectionRequestService.acceptRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
        try {
            connectionRequestService.rejectRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/connections/{userId}")
    public ResponseEntity<List<ConnectionRequest>> findConnectionsByUserId(@PathVariable Long userId) {
        List<ConnectionRequest> connections = connectionRequestService.findConnectionsByUserId(userId);
        return new ResponseEntity<>(connections, HttpStatus.OK);
    }

}
