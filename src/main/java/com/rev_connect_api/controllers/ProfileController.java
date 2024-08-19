package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.exceptions.InvalidProfileException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.services.ProfileService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/profile")
public class ProfileController {
  @Autowired
  ProfileService profileService;

  @GetMapping("/{user_id}")
  public ResponseEntity<PersonalProfile> retrieveProfile(@PathVariable Long user_id) { 
    PersonalProfile result;
    try {
      result = profileService.retrieveProfile(user_id);
      return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    } catch (InvalidUserException e) {
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
    }
  }

  @PutMapping()
  public ResponseEntity<Object> updateProfile(@RequestBody PersonalProfile profile) {
    PersonalProfile result;
    try {
      result = profileService.updateProfile(profile);
      return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    } catch (InvalidProfileException e) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(new FieldErrorResponse(e.getField(), e.getMessage()));
    } catch (InvalidUserException e) {
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(e.getMessage());
    }
  }
}
