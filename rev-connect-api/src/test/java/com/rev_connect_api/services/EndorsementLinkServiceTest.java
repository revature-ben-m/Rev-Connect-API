package com.rev_connect_api.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.repositories.EndorsementLinkRepository;

@ExtendWith(MockitoExtension.class)
class EndorsementLinkServiceTest {
    @Mock
    private EndorsementLinkRepository endorsementLinkRepository;

    @Mock
    private BusinessProfileRepository businessProfileRepository;

    @InjectMocks
    private EndorsementLinkService endorsementLinkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEndorsementLink() {
        Long userId = 1L;
        String link = "https://example.com";
        String linkText = "LinkedIn Profile";

        BusinessProfile businessProfile = new BusinessProfile();
        when(businessProfileRepository.findBusinessProfileByUserId(userId)).thenReturn(businessProfile);

        EndorsementLink endorsementLink = new EndorsementLink(userId, link, linkText);
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.save(any(EndorsementLink.class))).thenReturn(endorsementLink);

        EndorsementLink result = endorsementLinkService.createEndorsementLink(userId, link, linkText);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(link, result.getLink());
        assertEquals(linkText, result.getLinkText());

        verify(businessProfileRepository, times(1)).findBusinessProfileByUserId(userId);
        verify(endorsementLinkRepository, times(1)).save(any(EndorsementLink.class));
    }

    @Test
    void getAllEndorsementLinksByUserTest() {
        Long userId = 1L;

        BusinessProfile businessProfile = new BusinessProfile();
        when(businessProfileRepository.findBusinessProfileByUserId(userId)).thenReturn(businessProfile);

        List<EndorsementLink> list = new ArrayList<>();
        list.add(new EndorsementLink(userId, "https://example.com", "LinkedIn"));
        list.add(new EndorsementLink(2L, "https://google.com", "Google"));
        when(endorsementLinkRepository.findByBusinessUserId(userId)).thenReturn(list);

        List<EndorsementLink> result = endorsementLinkService.getEndorsementLinksByUserId(userId);

        assertThat(result).isNotNull();
        assertEquals(2, result.size());
    }

    @Test
    void updateEndorsementLinkTest() {
        Long userId = 1L;

        BusinessProfile businessProfile = new BusinessProfile();
        when(businessProfileRepository.findBusinessProfileByUserId(userId)).thenReturn(businessProfile);

        EndorsementLink endorsementLink  = new EndorsementLink(userId, "https://example.com", "linkedin");
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.findById(1L)).thenReturn(Optional.of(endorsementLink));

        when(endorsementLinkRepository.save(any(EndorsementLink.class))).thenReturn(endorsementLink);

        EndorsementLink updated = endorsementLinkService.updateEndorsementLink(1L, userId, "https://google.com", "updated link");

        assertNotNull(updated);
        assertEquals("https://google.com", updated.getLink());
        assertEquals("updated link", updated.getLinkText());      
    }

    @Test
    void deleteEndorsementLinkTest() {
        EndorsementLink endorsementLink  = new EndorsementLink(1L, "https://example.com", "linkedin");
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.findById(1L)).thenReturn(Optional.of(endorsementLink));

        endorsementLinkService.deleteEndorsementLink(1L);

        verify(endorsementLinkRepository, times(1)).delete(any(EndorsementLink.class));
    }
}
