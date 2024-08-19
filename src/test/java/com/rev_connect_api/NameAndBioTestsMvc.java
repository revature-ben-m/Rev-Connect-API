package com.rev_connect_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rev_connect_api.models.PersonalProfile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class NameAndBioTestsMvc {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(get("/api/profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void respondToRetrieveProfile() throws JsonMappingException, JsonProcessingException, Exception {
      mockMvc.perform(get("/api/profile/1")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
    }
        // ResponseEntity<String> response = testRestTemplate.getForEntity(serviceLocation + "/" + testUser.getUserId(), String.class);

        // int statusCode = response.getStatusCode().value();
        // Assertions.assertEquals(statusCode, 200, "Expected status code 200. Actual result was " + statusCode);

        

        // PersonalProfile actualResult = mapper.readValue(response.getBody().toString(), PersonalProfile.class);

        // Assertions.assertEquals(testBio, actualResult.getBio(), "Expected: " + testBio + "\nActual: " + actualResult.getBio());
        // Assertions.assertEquals(testUser, actualResult.getUser(), "Expected: " + testUser + "\nActual: " + actualResult.getUser());
}
