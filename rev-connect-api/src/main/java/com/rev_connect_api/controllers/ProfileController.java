package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.User;
import com.rev_connect_api.models.dto.UpdateProfileDto;
import com.rev_connect_api.services.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
  @Autowired
  ProfileService profileService;

  @GetMapping("/{id}")
  public PersonalProfile retrieveProfile(@PathVariable Long id) { 
    return profileService.retrieveProfile(id);
  }

  @PutMapping()
  public PersonalProfile updateProfile(@RequestBody PersonalProfile profile) {
    // User user = new User();
    // user.setId(profileDto.getUId());
    // user.setFirstName(profileDto.getFirstName());
    // user.setLastName(profileDto.getLastName());
    // PersonalProfile profile = new PersonalProfile(user, profileDto.getBio());
    return profileService.updateProfile(profile);
  }
}
