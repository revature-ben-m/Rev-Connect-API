package com.rev_connect_api.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId")
    private long commentId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "postId")
    private String postId;

    @Column(name = "text")
    private String text;

    @Column(name = "timePosted")
    private String timePosted;

    @Column(name = "likesCount")
    private Integer likesCount;

    public Comment(String userId, String postId, String text, String timePosted, Integer likesCount) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.timePosted = timePosted;
        this.likesCount = likesCount;
    }

    // Getters and Setters

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
        return "Comment{" +
                "commentId=" + commentId +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", text='" + text + '\'' +
                ", timePosted=" + timePosted +
                ", likesCount=" + likesCount +
                '}';
    }
}
