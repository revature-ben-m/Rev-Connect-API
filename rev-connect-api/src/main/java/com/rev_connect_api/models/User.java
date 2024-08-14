package com.rev_connect_api.models;

import org.springframework.data.annotation.Id;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
	
	public User(){}
	
	public User(Integer id,String userId, String firstName, String lastName,String userEmail,String password ) {
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "userEmail")
	private String userEmail;
	
	@Column(name = "userPwd")
	private String password;
	
	
	
	
}
