package com.rev_connect_api.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Column(name="postId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer postId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "text")
    private String text;

    @Column(name = "timePosted")
    private LocalDateTime timePosted;

    @Column(name = "likesCount")
    private Integer likesCount;

    public Post() {}

    public Post(int postId, String userId, String text, LocalDateTime timePosted, Integer likesCount) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.timePosted = timePosted;
        this.likesCount = likesCount;
    }

    // Getters and Setters

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    // toString method

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", timePosted=" + timePosted +
                ", likesCount=" + likesCount +
                '}';
    }
}
