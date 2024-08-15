package com.rev_connect_api.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Column(name="postId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long postId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "timePosted")
    private String timePosted;



    public Post() {}

    public Post(long userId, String text, String timePosted) {
        this.userId = userId;
        this.text = text;
        this.timePosted = timePosted;

    }

    // Getters and Setters

    public long getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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



    // toString method

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", timePosted=" + timePosted +
                '}';
    }
}
