package com.rev_connect_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="uId")
    private String uId = "";

    private String name = "";
    private String bio = "";

    public User()
    {

    }

    public User(String uId, String name, String bio) {
        this.uId = uId;
        this.name = name;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "{ uId: " + uId + ", name: " + name + ", bio: " + bio + " }";
    }
}
