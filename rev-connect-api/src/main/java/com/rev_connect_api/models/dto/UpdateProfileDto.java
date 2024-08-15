package com.rev_connect_api.models.dto;

public class UpdateProfileDto {
  private String firstName;
  private String lastName;
  private String bio;
  // This will be concealed in a session token
  private Long uId;

  public UpdateProfileDto(String firstName, String lastName, String bio, Long uId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.bio = bio;
    this.uId = uId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getBio() {
    return bio;
  }

  public Long getUId() {
    return uId;
  }


}
