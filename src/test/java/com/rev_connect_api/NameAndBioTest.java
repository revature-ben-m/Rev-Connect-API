package com.rev_connect_api;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.http.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.ProfileRepository;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.security.JwtUtil;
import com.rev_connect_api.utils.UserUtils;

//TODO: Authentication in methods
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NameAndBioTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    JwtUtil jwtUtil;

    @LocalServerPort
    private int port;

    private String serviceLocation;
    private ObjectMapper mapper;
    
    private User testUser;
    private String testBio;
    private String token;

    @BeforeEach
    public void beforeEach() {
        serviceLocation = "http://localhost:" + port + "/api/profile";
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        userRepository.deleteAll();
        profileRepository.deleteAll();

        testUser = new User("Username", "$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO","test@email.com", "Test", "User", false);
        userRepository.save(testUser);
        testBio = "Test bio!";
        PersonalProfile testProfile = new PersonalProfile(testUser, testBio);
        profileRepository.save(testProfile);

        token = jwtUtil.generateToken(testUser.getUsername(), Set.of("ROLE_USER"));
    }

    @Test
    public void respondToRetrieveProfile() throws JsonMappingException, JsonProcessingException  {
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + testUser.getUserId(), HttpMethod.GET, requestEntity,String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(200, statusCode, "Expected status code 200. Actual result was " + statusCode);

        PersonalProfile actualResult = mapper.readValue(response.getBody().toString(), PersonalProfile.class);

        Assertions.assertEquals(testBio, actualResult.getBio(), "Expected: " + testBio + "\nActual: " + actualResult.getBio());
        Assertions.assertEquals(testUser, actualResult.getUser(), "Expected: " + testUser + "\nActual: " + actualResult.getUser());
    }

    @Test
    public void respondToUpdateProfileSuccessful() throws JsonMappingException, JsonProcessingException {
        
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity,String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(200, statusCode, "Expected status code 200. Actual result was " + statusCode);
    
        PersonalProfile profile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
        
        Assertions.assertEquals("Doe, John", UserUtils.getFullName(profile.getUser()), "Expected: Doe, John\nActual result was " + UserUtils.getFullName(profile.getUser()));
        Assertions.assertEquals("Example bio!", profile.getBio(), "Expected: Example bio!\nActual result was " + profile.getBio());
    }

    @Test
    public void processUpdateProfileSuccessful() {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);
        
        PersonalProfile profile = profileRepository.findByUser_UserId(testUser.getUserId()).get();

        Assertions.assertEquals("Example bio!", profile.getBio(), "Expected: Example bio!\nActual result was " + profile.getBio());
        Assertions.assertEquals("Doe, John", UserUtils.getFullName(profile.getUser()), "Expected: Doe, John\nActual result was " + UserUtils.getFullName(profile.getUser()));
    }

    @Test
    public void respondToUpdateProfileEmptyFirstName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User();
        newUser.setFirstName("");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);
    
        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("firstName", "First name must not be empty."), "Expected first name field error.");
    }
    
    @Test
    public void respondToUpdateProfileEmptyLastName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getUserId());
        newUser.setFirstName("John");
        newUser.setLastName("");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("lastName", "Last name must not be empty."), "Expected last name field error.");
    }

    @Test
    public void respondToUpdateProfileTooLongFirstName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getUserId());
        newUser.setFirstName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("firstName", "First name is too long."), "Expected first name field error.");
    }

    @Test
    public void respondToUpdateProfileTooLongLastName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getUserId());
        newUser.setFirstName("John");
        newUser.setLastName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("lastName", "Last name is too long."), "Expected last name field error.");
    }

    @Test
    public void respondToUpdateProfileTooLongBio() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getUserId());
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("bio", "Bio is too long."), "Expected bio field error.");
    }

    @Test
    public void processUpdateProfileInvalidProfile() {
        User newUser = new User(testUser.getUserId());
        newUser.setFirstName("");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile, headers);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        PersonalProfile actualProfile = profileRepository.findByUser_UserId(testUser.getUserId()).get();
        Assertions.assertEquals(testBio, actualProfile.getBio(), "Expected: " + testBio + "\nActual result was " + actualProfile.getBio());
        Assertions.assertEquals(UserUtils.getFullName(testUser), UserUtils.getFullName(actualProfile.getUser()), "Expected: " + UserUtils.getFullName(testUser) + "\nActual result was " + UserUtils.getFullName(actualProfile.getUser()));
    }
}
