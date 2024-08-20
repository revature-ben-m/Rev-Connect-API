package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        BusinessProfile findBusinessProfile = businessProfileRepository.findByUserId(userId);
        if (findBusinessProfile != null) {
            return findBusinessProfile;
        }
        return null;
    }

    public Map<String, Object> findAllProfileInfoByUserId (long userId) {
        Map<String, Object> findBusinessProfile = businessProfileRepository.findAllProfileInfoByUserId(userId);
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

    public Map<String, Object> updateBioText(BusinessProfile businessProfile, long userId) {
        String updatedBioText = businessProfile.getBioText();
        if (updatedBioText.length() > 500) {
            throw new BioTextTooLongException("Exceeding 500 character limit");
        }
        BusinessProfile findBusinessProfile = businessProfileRepository.findByUserId(userId);
        if (findBusinessProfile != null) {
            findBusinessProfile.setBioText(updatedBioText);
            businessProfileRepository.save(findBusinessProfile);
            return businessProfileRepository.findAllProfileInfoByUserId(userId);
        }
        return null;
    }
        
}
    
