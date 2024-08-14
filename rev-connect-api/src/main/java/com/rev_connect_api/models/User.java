package com.rev_connect_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="uId")
    private String uId = "";

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn()
    private PersonalProfile profile;

    public User()
    {

    }

    public User(String uId) {
        this.uId = uId;
    }

    public String getId() {
        return this.uId;
    }

    @Override
    public String toString() {
        return "{ uId: " + uId + " }";
    }
}
