package com.rev_connect_api.models;

import jakarta.persistence.*;

@Entity
@Table(name="PostLikes")
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postLikesId")
    private long postLikesId;

    @Column(name = "userId")
    private long userId;

    @Column(name="postId")
    private long postId;

    @Column(name="timePosted")
    private long timePosted;

    public PostLikes() {}
    public PostLikes(long postLikesId, long userId, long postId, long timePosted) {
        this.userId = userId;
        this.postId = postId;
        this.timePosted = timePosted;
    }
    public long getPostLikesId() {
        return postLikesId;
    }
    public void setPostLikesId(long postLikesId) {
        this.postLikesId = postLikesId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getPostId() {
        return postId;
    }
    public void setPostId(long postId) {
        this.postId = postId;
    }
    public long getTimePosted() {
        return timePosted;
    }
    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }


}
