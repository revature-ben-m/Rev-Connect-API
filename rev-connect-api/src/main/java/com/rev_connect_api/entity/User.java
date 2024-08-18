package com.rev_connect_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "system_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "requester")
    @JsonIgnore
    private List<ConnectionRequest> sentRequests;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<ConnectionRequest> receivedRequests;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ConnectionRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<ConnectionRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public List<ConnectionRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(List<ConnectionRequest> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }
}
