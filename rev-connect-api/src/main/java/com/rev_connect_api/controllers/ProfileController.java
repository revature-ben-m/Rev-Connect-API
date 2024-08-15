package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.services.BusinessProfileService;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class ProfileController {

    @Autowired
    private BusinessProfileService businessProfileService;

    @GetMapping("/profiles/business/{userId}")
    public ResponseEntity<BusinessProfile> getBusinessProfileByUserId(@PathVariable long userId) {
        BusinessProfile resultBusinessProfile = businessProfileService.findByUserId(userId);
        if (resultBusinessProfile != null) {
            return new ResponseEntity<>(resultBusinessProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profiles/business")
    public ResponseEntity<List<BusinessProfile>> getBusinessProfiles() {
        return new ResponseEntity<>(businessProfileService.findAllBusinessProfiles(), HttpStatus.OK);
    }

    @PostMapping("/profiles/business")
    public ResponseEntity<BusinessProfile> createNewBusinessProfile(@RequestBody BusinessProfile businessProfile) {
        BusinessProfile confirmCreate = businessProfileService.createBusinessProfile(businessProfile);
        if (confirmCreate != null) {
            return new ResponseEntity<>(confirmCreate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/profiles/business/{userId}")
    public ResponseEntity<BusinessProfile> updateBioTextForBusinessProfile (
            @RequestBody BusinessProfile businessProfile,
            @PathVariable long userId
            ) {
        BusinessProfile confirmUpdate = businessProfileService.updateBioText(businessProfile, userId);
        if (confirmUpdate != null) {
            return new ResponseEntity<>(confirmUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

