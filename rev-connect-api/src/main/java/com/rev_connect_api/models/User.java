package com.rev_connect_api.models;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

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

	@Column(name = "isBusiness")
	private Boolean isBusiness;
	



	public User(){}
	
	public User(String userId, String firstName, String lastName,String userEmail,String password,Boolean isBusiness ) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
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


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Boolean getBusiness() {
		return isBusiness;
	}

	public void setBusiness(Boolean business) {
		isBusiness = business;
	}
}
