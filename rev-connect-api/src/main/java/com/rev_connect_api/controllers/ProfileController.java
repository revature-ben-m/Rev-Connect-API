package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.PersonalProfile;
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


}
