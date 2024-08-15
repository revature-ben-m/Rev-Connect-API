package com.rev_connect_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.services.BusinessProfileService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class HomeController {
    @Autowired
    private BusinessProfileService businessProfileService;

    @GetMapping("/profiles/business/{userId}")
    public ResponseEntity<BusinessProfile> getBusinessProfileByUserId(@PathVariable long userId) {
        BusinessProfile resultBusinessProfile = businessProfileService.findByUserId(userId);
        System.out.println("this is in controller " + resultBusinessProfile);
        return new ResponseEntity<>(resultBusinessProfile, HttpStatus.OK);
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
    // Optional<Account> check = accountService.findAccountById(newMessage.getPostedBy());
    //     if (check.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    //     return new ResponseEntity<>(messageService.createMessage(newMessage), HttpStatus.OK);
    // }
    
    
}
