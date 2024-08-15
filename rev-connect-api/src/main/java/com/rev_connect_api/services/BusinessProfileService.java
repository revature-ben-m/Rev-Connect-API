package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.exceptions.BioTextTooLongException;
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
        return allList;
    }

    public BusinessProfile createBusinessProfile(BusinessProfile businessProfile) {
        businessProfileRepository.save(businessProfile);
        Optional<BusinessProfile> oProfile = businessProfileRepository.findById(businessProfile.getId());
        BusinessProfile profile = oProfile.get();
        return profile;
    }

    public BusinessProfile updateBioText(BusinessProfile businessProfile, long userId) {
        String updatedBioText = businessProfile.getBioText();
        if (updatedBioText.length() > 500) {
            throw new BioTextTooLongException("Exceeding 500 character limit");
        }
        BusinessProfile findBusinessProfile = businessProfileRepository.findBusinessProfileByUserId(userId);
        if (findBusinessProfile != null) {
            findBusinessProfile.setBioText(updatedBioText);
            businessProfileRepository.save(findBusinessProfile);
            return findBusinessProfile;
        }
        return null;
    }
        
}
    
