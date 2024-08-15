package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.services.EndorsementLinkService;

@RestController
@RequestMapping("/api/endorsement_links")
public class EndorsementLinkController {

  @Autowired
  private EndorsementLinkService endorsementLinkService;

  ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping
  public ResponseEntity<EndorsementLink> createEndorsementLink(@RequestBody String endorsementLinkString) {
    try {
      EndorsementLink endorsementLink = objectMapper.readValue(endorsementLinkString, EndorsementLink.class);
      EndorsementLink createdEndorsementLink = endorsementLinkService.createEndorsementLink(
          endorsementLink.getUser(),
          endorsementLink.getLink(),
          endorsementLink.getDescription());
      return ResponseEntity.ok(createdEndorsementLink);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<EndorsementLink>> getEndorsementLinksByUserId(@RequestParam Long userId) {
    try {
      List<EndorsementLink> endorsementLinks = endorsementLinkService.getEndorsementLinksByUserId(userId);
      return ResponseEntity.ok(endorsementLinks);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @PatchMapping
  public ResponseEntity<EndorsementLink> updateEndorsementLink(@RequestBody String endorsementLinkString) {
    try {
      EndorsementLink endorsementLink = objectMapper.readValue(endorsementLinkString, EndorsementLink.class);
      EndorsementLink updatedEndorsementLink = endorsementLinkService.updateEndorsementLink(
          endorsementLink.getId(),
          endorsementLink.getUser(),
          endorsementLink.getLink(),
          endorsementLink.getDescription());
      return ResponseEntity.ok(updatedEndorsementLink);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteEndorsementLink(@RequestParam Long endorsementLinkId) {
    try {
      endorsementLinkService.deleteEndorsementLink(endorsementLinkId);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
}
