package com.rev_connect_api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.BusinessProfileService;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileControllerTest {
    
    @Mock private BusinessProfileService businessProfileService;
    @InjectMocks public BusinessProfileController underTest;
    
    @Test
    public void getAllBusinessProfilesReturns200() {
        final List<BusinessProfile> profiles = List.of(
            BusinessProfileTestDataUtil.createTestProfileA(),
            BusinessProfileTestDataUtil.createTestProfileB()
        );
        when(businessProfileService.findAllBusinessProfiles()).thenReturn(profiles);
        final ResponseEntity<List<BusinessProfile>> result = underTest.getBusinessProfiles();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllBusinessProfilesEmptyReturns200() {
        final List<BusinessProfile> profiles = List.of();
        when(businessProfileService.findAllBusinessProfiles()).thenReturn(profiles);
        final ResponseEntity<List<BusinessProfile>> result = underTest.getBusinessProfiles();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBusinessProfileByUserIdReturns200() {
        final HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("test", "test1");
        when(businessProfileService.findAllProfileInfoByUserId(111)).thenReturn(myMap);
        final ResponseEntity<Map<String,Object>> result = underTest.getBusinessProfileByUserId(111);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBusinessProfileByUserIdUserNotFoundReturns404() {
        final ResponseEntity<Map<String,Object>> result = underTest.getBusinessProfileByUserId(10);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void createNewBusinessProfileSuccessReturns200() {
        final BusinessProfile profile = BusinessProfileTestDataUtil.createTestProfileA();
        when(businessProfileService.createBusinessProfile(profile))
            .thenReturn(profile);
        final ResponseEntity<BusinessProfile> result = underTest.createNewBusinessProfile(profile);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createNewBusinessProfileUnsuccessReturns400() {
        final BusinessProfile profile = BusinessProfileTestDataUtil.createTestProfileA();
        when(businessProfileService.createBusinessProfile(profile))
            .thenReturn(null);
        final ResponseEntity<BusinessProfile> result = underTest.createNewBusinessProfile(profile);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateBioTextSuccessReturns200() {
        final long userId = 111;
        final BusinessProfile oldBioProfile = BusinessProfileTestDataUtil.createTestProfileA();
        final Map<String,Object> expectedMap = new HashMap<>();
        expectedMap.put("BIO_TEXT", "UPDATED BIO TEXT!");
        expectedMap.put("EMAIL", "test1@email");
        expectedMap.put("FIRSTNAME", "joe1");
        expectedMap.put("IS_BUSINESS", true);
        expectedMap.put("LASTNAME", "doe1");
        expectedMap.put("PROFILE_ID", 999);
        expectedMap.put("USERNAME", "test1");
        expectedMap.put("USER_ID", 111);
        final BusinessProfile updatedBioProfile = new BusinessProfile(999, "UPDATED TEST BIO!", new User((long) 111, "test1", "pw1", "joe1", "doe1", "test1@email", true, BusinessProfileTestDataUtil.createTestProfileA()));
        when(businessProfileService.updateBioText(updatedBioProfile, userId))
            .thenReturn(expectedMap);
        final ResponseEntity<Map<String,Object>> result = underTest.updateBioTextForBusinessProfile(updatedBioProfile, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateBioTextUnSuccessfulReturns409() {
        final long userId = 111;
        final BusinessProfile oldBioProfile = BusinessProfileTestDataUtil.createTestProfileA();
        final BusinessProfile updatedBioProfile = new BusinessProfile(999, "UPDATED TEST BIO!", new User((long) 111, "test1", "pw1", "joe1", "doe1", "test1@email", true, BusinessProfileTestDataUtil.createTestProfileA()));
        when(businessProfileService.updateBioText(updatedBioProfile, userId))
            .thenReturn(null);
        final ResponseEntity<Map<String,Object>> result = underTest.updateBioTextForBusinessProfile(updatedBioProfile, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
