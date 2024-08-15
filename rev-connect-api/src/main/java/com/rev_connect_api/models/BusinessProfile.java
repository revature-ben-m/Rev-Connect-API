package com.rev_connect_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "businessprofile")
public class BusinessProfile {
    
    @Column(name = "profile_id")
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @Column(name = "bio_text")
    private String bioText;

    public BusinessProfile() {
    }

    public BusinessProfile(long userId, String bioText) {
        this.userId = userId;
        this.bioText = bioText;
    }

    public BusinessProfile(long id, long userId, String bioText) {
        this.id = id;
        this.userId = userId;
        this.bioText = bioText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + ((bioText == null) ? 0 : bioText.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BusinessProfile other = (BusinessProfile) obj;
        if (id != other.id)
            return false;
        if (userId != other.userId)
            return false;
        if (bioText == null) {
            if (other.bioText != null)
                return false;
        } else if (!bioText.equals(other.bioText))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BusinessProfile{" +
            "id=" + id + 
            ", userId=" + userId + 
            ", bioText='" + bioText + '\'' +
            "}";
    }

}
