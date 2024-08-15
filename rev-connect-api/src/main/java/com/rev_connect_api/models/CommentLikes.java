package com.rev_connect_api.models;

import jakarta.persistence.*;

@Entity
@Table(name="CommentLikes")
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="commentLikeId")
    private long commentLikeId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "postId")
    private long postId;

    @Column(name = "timePosted")
    private String timePosted;



}
