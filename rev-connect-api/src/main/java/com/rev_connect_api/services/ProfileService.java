package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.ProfileRepository;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class ProfileService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  ProfileRepository profileRepository;
  
  public ProfileService() {

  }

  public ProfileService(UserRepository userRepository, ProfileRepository profileRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
  }

  public PersonalProfile retrieveProfile(long uId) {
    Optional<PersonalProfile> optionalProfile = profileRepository.findByUser_UId(uId);
    if(optionalProfile.isPresent()) {
      PersonalProfile ret = optionalProfile.get();
      return ret;
    } else {
      return null;
    }
    
  }

  public PersonalProfile updateProfile(PersonalProfile newProfile) {
    Optional<PersonalProfile> optionalProfile = profileRepository.findByUser_UId(newProfile.getUser().getId());
    if(!optionalProfile.isPresent()) {
      // throw new exception
      return new PersonalProfile(null, "Wrong!");
    }
    PersonalProfile profile = optionalProfile.get();
    profile.getUser().setFirstName(newProfile.getUser().getFirstName());
    profile.getUser().setLastName(newProfile.getUser().getLastName());
    profile.setBio(newProfile.getBio());
    profileRepository.save(profile);
    return profile;
  }
  
}
