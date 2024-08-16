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
    private Integer userId;
    private String email;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    private Boolean isBusiness;

    public User(){
        this.userId = 9001;
        this.email = "test@revature.net";
        this.username = "test";
        this.password = "testpassword";
        this.isBusiness = false;
    }
    public User(Integer userId, String email, String username, String password, Boolean isBusiness){
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isBusiness = isBusiness;
    }

    public User(String email, String username, String password, Boolean isBusiness){
        this.email = email;
        this.username = username;
        this.password = password;
        this.isBusiness = isBusiness;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
                ", password='" + password + '\'' +
                '}';
    }
}
