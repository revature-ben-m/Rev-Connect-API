package com.rev_connect_api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.services.BusinessProfileService;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileControllerTest {
    
    @Mock private BusinessProfileService businessProfileService;
    @InjectMocks public ProfileController underTest;
    
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
        final BusinessProfile expected = BusinessProfileTestDataUtil.createTestProfileA();
        when(businessProfileService.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId()))
            .thenReturn(expected);
        final ResponseEntity<BusinessProfile> result = underTest.getBusinessProfileByUserId(111);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBusinessProfileByUserIdUserNotFoundReturns400() {
        when(businessProfileService.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId()))
            .thenReturn(null);
        final ResponseEntity<BusinessProfile> result = underTest.getBusinessProfileByUserId(111);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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
        final BusinessProfile updatedBioProfile = new BusinessProfile(999, 111, "UPDATED TEST BIO!");
        when(businessProfileService.updateBioText(updatedBioProfile, userId))
            .thenReturn(updatedBioProfile);
        final ResponseEntity<BusinessProfile> result = underTest.updateBioTextForBusinessProfile(updatedBioProfile, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateBioTextUnSuccessReturns200() {
        final long userId = 111;
        final BusinessProfile oldBioProfile = BusinessProfileTestDataUtil.createTestProfileA();
        when(businessProfileService.updateBioText(oldBioProfile, userId))
            .thenReturn(oldBioProfile);
        final ResponseEntity<BusinessProfile> result = underTest.updateBioTextForBusinessProfile(oldBioProfile, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
