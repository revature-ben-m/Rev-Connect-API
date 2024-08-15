package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.models.BusinessProfile;
import java.util.*;
@Service
public class BusinessProfileService {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    public BusinessProfile findByUserId (long userId) {
        BusinessProfile findBusinessProfile = businessProfileRepository.findBusinessProfileByUserId(userId);
        if (findBusinessProfile != null) {
            return findBusinessProfile;
        }
        return null;
    }

    public List<BusinessProfile> findAllBusinessProfiles() {
        List<BusinessProfile> allList = businessProfileRepository.findAll();
        System.out.println(allList);
        return allList;
    }

    public BusinessProfile createBusinessProfile(BusinessProfile businessProfile) {
            businessProfileRepository.save(businessProfile);
            return businessProfileRepository.findById(businessProfile.getId()).get();
    }
        
}
    
