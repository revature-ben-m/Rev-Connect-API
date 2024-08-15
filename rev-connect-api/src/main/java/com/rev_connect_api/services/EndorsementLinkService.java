package com.rev_connect_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.repositories.EndorsementLinkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EndorsementLinkService {
    @Autowired
    private EndorsementLinkRepository endorsementLinkRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    public EndorsementLinkService(EndorsementLinkRepository endorsementLinkRepository,
            BusinessProfileRepository businessProfileRepository) {
        this.endorsementLinkRepository = endorsementLinkRepository;
        this.businessProfileRepository = businessProfileRepository;
    }

    public EndorsementLinkService() {
    }

    public EndorsementLink createEndorsementLink(Long businessUserId, String link, String description) {
        BusinessProfile businessProfile = businessProfileRepository.findBusinessProfileByUserId(businessUserId);

        if (businessProfile == null) {
            throw new IllegalArgumentException("User does not have a business profile: " + businessUserId);
        }
        EndorsementLink endorsementLink = new EndorsementLink(businessUserId, link, description);
        return endorsementLinkRepository.save(endorsementLink);
    }

    public List<EndorsementLink> getEndorsementLinksByUserId(Long businessUserId) {
        return endorsementLinkRepository.findByUserId(businessUserId);
    }

    public EndorsementLink updateEndorsementLink(Long id, Long businessUserId, String link, String description) {
        BusinessProfile businessProfile = businessProfileRepository.findBusinessProfileByUserId(businessUserId);

        if (businessProfile == null) {
            throw new IllegalArgumentException("User does not have a business profile: " + businessUserId);
        }

        EndorsementLink endorsementLink = endorsementLinkRepository.findById(id).orElse(null);

        if (endorsementLink == null) {
            throw new IllegalArgumentException("Endorsement link not found: " + id);
        }

        endorsementLink.setUser(businessUserId);
        endorsementLink.setLink(link);
        endorsementLink.setDescription(description);

        return endorsementLinkRepository.save(endorsementLink);
    }

    public void deleteEndorsementLink(Long endorsementLinkId) {
        EndorsementLink endorsementLink = endorsementLinkRepository.findById(endorsementLinkId).orElse(null);

        if (endorsementLink == null) {
            throw new IllegalArgumentException("Endorsement link not found: " + endorsementLinkId);
        }

        endorsementLinkRepository.delete(endorsementLink);
    }

}