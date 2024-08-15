package com.rev_connect_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileServiceTest {

    @Mock private BusinessProfileRepository businessProfileRepository;
    @InjectMocks private BusinessProfileService underTest;
    
    @Test
    public void findByUserIdFound() {
        final BusinessProfile expected = BusinessProfileTestDataUtil.createTestProfileA();

        when(businessProfileRepository.findBusinessProfileByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId()))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId());
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @Test
    public void findByUserIdNotFound() {
        final BusinessProfile expected = null;

        when(businessProfileRepository.findBusinessProfileByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId()))
            .thenReturn(null);

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUserId());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void findAllBusinessProfilesReturnsAListWhenPresent() {
        final List<BusinessProfile> profileList = 
            List.of(
                BusinessProfileTestDataUtil.createTestProfileA(),
                BusinessProfileTestDataUtil.createTestProfileB(),
                BusinessProfileTestDataUtil.createTestProfileC()
            );
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotEmpty();
    }

    @Test
    public void findAllBusinessProfilesReturnsEmptyListIfNone() {
        final List<BusinessProfile> profileList = 
            List.of();
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotNull()
            .isEmpty();
    }

    @Test
    public void updateBioTextUpdatesBioText() {
        String expected = "Test Bio 3";
        when(businessProfileRepository.findBusinessProfileByUserId((long) 111))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());
        String actual = (underTest.updateBioText(BusinessProfileTestDataUtil.createTestProfileC(), BusinessProfileTestDataUtil.createTestProfileA().getUserId())).getBioText();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateBioTextFailsWhenTextTooLong() throws BioTextTooLongException {
        assertThatExceptionOfType(BioTextTooLongException.class)
            .isThrownBy(() -> underTest.updateBioText(BusinessProfileTestDataUtil.createTestProfileD(), 111));
    }

}
