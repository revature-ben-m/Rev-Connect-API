package com.rev_connect_api.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_profile")
public class PersonalProfile {
    @Id
    @JoinColumn(name = "user_id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String name = "";
    private String bio = "";

    public PersonalProfile() {
        
    }

    public PersonalProfile(User user, String name, String bio){
        this.user = user;
        this.id = user.getId();
        this.name = name;
        this.bio = bio;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getBio(){
        return this.bio;
    }

    @Override
    public String toString() {
        return "{ User: " + user + ", name: " + name + ", bio: " + bio + " }";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }

        PersonalProfile otherProfile = (PersonalProfile) o;
        return Objects.equals(this.id, otherProfile.getId()) && Objects.equals(this.name, otherProfile.getName()) && Objects.equals(this.bio, otherProfile.getBio());
    }
}
