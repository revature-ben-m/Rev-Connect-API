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
    if(!optionalProfile.isPresent()) {
      return new PersonalProfile(null, "Wrong Bio!");
      // Handle error
    } else {
      return optionalProfile.get();
    }
    
  }
  
}
