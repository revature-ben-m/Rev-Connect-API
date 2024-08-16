package com.rev_connect_api.models;
import jakarta.persistence.*;

/**
 * Represents a user in the system.
 * This class maps to the "Users" table in the database and contains user-related information.
 */
@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "userName")
	private String userName;

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


	/**
	 * Default constructor for JPA.
	 */
	public User(){}

	/**
	 * Constructs a new User with the specified details.
	 *
	 * @param userName - the username of the user.
	 * @param firstName - the first name of the user.
	 * @param lastName - the last name of the user.
	 * @param userEmail - the email address of the user.
	 * @param password - the password of the user.
	 * @param isBusiness - a boolean indicating whether the user is a business account.
	 */
	public User(String userName, String firstName, String lastName,String userEmail,String password,Boolean isBusiness ) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
	}

	/**
	 * Gets the username of the user.
	 *
	 * @return the username of the user.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param userName - the username to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the first name of the user.
	 *
	 * @return - the first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName - the first name to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the user.
	 *
	 * @return - the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName - the last name to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email address of the user.
	 *
	 * @return - the email address of the user.
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the email address of the user.
	 *
	 * @param userEmail - the email address to set.
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * Gets the password of the user.
	 *
	 * @return - the password of the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password - the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the ID of the user.
	 *
	 * @param id - the ID to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the ID of the user.
	 *
	 * @return - the ID of the user.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Checks if the user is a business account.
	 *
	 * @return - true if the user is a business account, false otherwise.
	 */
	public Boolean getBusiness() {
		return isBusiness;
	}

	/**
	 * Sets whether the user is a business account.
	 *
	 * @param business - true if the user if a business account, false otherwise.
	 */
	public void setBusiness(Boolean business) {
		isBusiness = business;
	}
}
