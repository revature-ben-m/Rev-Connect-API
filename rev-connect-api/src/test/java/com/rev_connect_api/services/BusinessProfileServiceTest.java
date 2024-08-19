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

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
        assertThat(result).isNotNull().hasToString(expected.toString());
    }

    @Test
    public void findByUserIdNotFound() {
        final BusinessProfile expected = null;

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(null);

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
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
        when(businessProfileRepository.findByUserId((long) 111))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());
        String actual = (underTest.updateBioText(BusinessProfileTestDataUtil.createTestProfileC(), BusinessProfileTestDataUtil.createTestProfileA().getUser().getId())).getBioText();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateBioTextFailsWhenTextTooLong() throws BioTextTooLongException {
        assertThatExceptionOfType(BioTextTooLongException.class)
            .isThrownBy(() -> underTest.updateBioText(BusinessProfileTestDataUtil.createTestProfileD(), 111));
    }

    @Test
    public void findFullProfileByUserIdFound() {
        final Map<String, Object> myMap = new HashMap<>();
       myMap.put("EMAIL", "test2@email"); 
       myMap.put("LASTNAME", "doe2");
       myMap.put("PROFILE_ID", 998);
       myMap.put("FIRSTNAME", "joe2");
       myMap.put("USER_ID", 112);
       myMap.put("USERNAME", "test2");
       myMap.put("IS_BUSINESS", true);
       myMap.put("BIO_TEXT", "Test Bio 2");

        when(businessProfileRepository.findAllProfileInfoByUserId(112))
            .thenReturn(myMap);
        final Map<String, Object> result = underTest.findAllProfileInfoByUserId(112);
        assertThat(result).isNotEmpty().hasFieldOrProperty("EMAIL").hasFieldOrProperty("LASTNAME").hasFieldOrPropertyWithValue("USER_ID", 112);
    }

    @Test
    public void findFullProfileByUserIdNotFound() {
        final Map<String, Object> myMap = new HashMap<>();
        when(businessProfileRepository.findAllProfileInfoByUserId(10))
        .thenReturn(myMap);
        final Map<String, Object> result = underTest.findAllProfileInfoByUserId(10);
        assertThat(result).isEmpty();
    }


}
