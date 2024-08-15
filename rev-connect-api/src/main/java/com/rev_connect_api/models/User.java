package com.rev_connect_api.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "username")
	private String username;

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
	
	public User(String username, String firstName, String lastName,String userEmail,String password,Boolean isBusiness ) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userId) {
		this.username = username;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userEmail=" + userEmail + ", password=" + password + ", isBusiness=" + isBusiness + "]";
	}

	
}
