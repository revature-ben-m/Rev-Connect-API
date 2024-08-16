package com.rev_connect_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String userPwd;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isBusiness;


    public User(){
        this.userId = 9001L;
        this.email = "test@revature.net";
        this.username = "test";
        this.userPwd = "testpassword";
        this.isBusiness = false;
    }

    public User(Long userId, String username, String userPwd){
        this.userId = userId;
        this.username = username;
        this.userPwd = userPwd;
    }

    public User(String username, String userPwd){
        this.username = username;
        this.userPwd = userPwd;
    }

    public User(String username, String userPwd, String email, String firstName, String lastName, boolean isBusiness) {
        this.username = username;
        this.userPwd = userPwd;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBusiness = isBusiness;
    }

    public User(String email, String username, String userPwd, Boolean isBusiness){
        this.email = email;
        this.username = username;
        this.userPwd = userPwd;
        this.isBusiness = isBusiness;
    }

    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return userPwd;
    }

    public void setPassword(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Boolean getIsBusiness() {
        return isBusiness;
    }
    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userPwd == null) {
			if (other.userPwd != null)
				return false;
		} else if (!userPwd.equals(other.userPwd))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + userPwd + '\'' +
                '}';
    }
}
