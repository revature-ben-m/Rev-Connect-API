package com.rev_connect_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "businessprofile")
public class BusinessProfile {
    
    @Column(name = "profile_id")
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

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

    public BusinessProfile(Long id, long userId, String bioText) {
        this.id = id;
        this.userId = userId;
        this.bioText = bioText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BusinessProfile other = (BusinessProfile) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
