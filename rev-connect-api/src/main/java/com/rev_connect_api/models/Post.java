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
    private String timePosted;

    @Column(name = "likesCount")
    private Integer likesCount;

    public Post() {}

    public Post(String userId, String text, String timePosted, Integer likesCount) {
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

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
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
