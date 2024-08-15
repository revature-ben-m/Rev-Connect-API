package com.rev_connect_api.controllers;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @RequestMapping("/")
    public String hello()
    {
        return "Hello Localhost World!!";
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        List<Post> res = postService.getAllPosts();
        System.out.println(res);
        return res;
    }

    @PostMapping(value="/posts")
    public void createPosts() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        LocalDateTime now = LocalDateTime.now();

        String dateTimeString = now.format(formatter);
        
        Post post = new Post("username", "First post", dateTimeString, 0 );
        postService.createPost(post);
    }
}
