package com.rev_connect_api.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer postId;
    private LocalDateTime createdAt;
    private String text;

    public Post() {}

    public Post(String text, LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.text = text;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}