package com.rev_connect_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
public class Post {
    @Column(name="postId")
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    @Column(name="postBy")
    private Integer postedBy;
    @Column(name="postText")
    private String postText;
    @Column(name="timePostedEpoch")
    private Long timePostedEpoch;

    public Post(Integer postId, Integer postedBy, String postText, Long timePostedEpoch) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    public Post(Integer postedBy, String postText, Long timePostedEpoch) {
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
}
