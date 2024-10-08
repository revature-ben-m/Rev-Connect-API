package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.InvalidProfileException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.repositories.ProfileRepository;

@Service
public class ProfileService {
  private ProfileRepository profileRepository;
  
  public ProfileService() {

  }

  @Autowired
  public ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  public PersonalProfile retrieveProfile(long user_id) throws InvalidUserException {
    Optional<PersonalProfile> optionalProfile = profileRepository.findByUser_UserId(user_id);
    if(!optionalProfile.isPresent()) {
      throw new InvalidUserException("User " + user_id + " was not found.");
    }
    return optionalProfile.get();
  }

  public PersonalProfile updateProfile(PersonalProfile newProfile) throws InvalidProfileException, InvalidUserException {
    // Validate profile fields
    boolean firstNameEmpty = "".equals(newProfile.getUser().getFirstName());
    boolean firstNameTooLong = newProfile.getUser().getFirstName().length() > 50;
    
    boolean lastNameEmpty = "".equals(newProfile.getUser().getLastName());
    boolean lastNameTooLong = newProfile.getUser().getLastName().length() > 50;

    boolean bioTooLong = newProfile.getBio().length() > 255;

    if (firstNameEmpty)
    {
      throw new InvalidProfileException("firstName", "First name must not be empty.");
    }
    if (firstNameTooLong)
    {
      throw new InvalidProfileException("firstName", "First name is too long.");
    }

    if (lastNameEmpty)
    {
      throw new InvalidProfileException("lastName", "Last name must not be empty.");
    }
    if (lastNameTooLong)
    {
      throw new InvalidProfileException("lastName", "Last name is too long.");
    }

    if(bioTooLong) {
      throw new InvalidProfileException("bio", "Bio is too long.");
    }

    // Retrieve profile from repository
    Optional<PersonalProfile> optionalProfile = profileRepository.findByUser_Username(newProfile.getUser().getUsername());
    if(!optionalProfile.isPresent()) {
      throw new InvalidUserException("User " + newProfile.getUser().getUserId() + " was not found.");
    }

    PersonalProfile profile = optionalProfile.get();

    // Update profile repository
    profile.getUser().setFirstName(newProfile.getUser().getFirstName());
    profile.getUser().setLastName(newProfile.getUser().getLastName());
    profile.setBio(newProfile.getBio());
    profileRepository.save(profile);
    return profile;
  }
  
}
