package com.rev_connect_api;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    
    private final User initialUser, validUser, userNoFirstName, userNoLastName, userFirstNameTooLong, userLastNameTooLong;
    private final PersonalProfile initialProfile, validProfile, profileEmptyFirstName, profileEmptyLastName, profileFirstNameTooLong, profileLastNameTooLong, profileBioTooLong;
    private String token;

    public NameAndBioTest() {
        initialUser = new User("user", "","", "Test", "User", false);
        validUser = new User("user", "","", "John", "Doe", false);
        userNoFirstName = new User("user", "","", "", "Doe", false);
        userNoLastName = new User("user", "","", "John", "", false);
        userFirstNameTooLong = new User("user", "","", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Doe", false);
        userLastNameTooLong = new User("user", "","", "John", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", false);
        initialProfile = new PersonalProfile(initialUser, "Initial bio");
        validProfile = new PersonalProfile(validUser, "Valid bio");
        profileEmptyFirstName = new PersonalProfile(userNoFirstName, "Valid bio");
        profileEmptyLastName = new PersonalProfile(userNoLastName, "Valid bio");
        profileFirstNameTooLong = new PersonalProfile(userFirstNameTooLong, "Valid bio");
        profileLastNameTooLong = new PersonalProfile(userLastNameTooLong, "Valid bio");
        profileBioTooLong = new PersonalProfile(validUser, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @BeforeEach
    public void beforeEach() {
        serviceLocation = "http://localhost:" + port + "/api/profile";
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        userRepository.deleteAll();
        profileRepository.deleteAll();

        userRepository.save(initialUser);
        profileRepository.save(initialProfile);

        token = jwtUtil.generateToken(initialUser.getUsername(), Set.of("ROLE_USER"));
    }

    @Test
    public void respondToRetrieveProfile() throws JsonMappingException, JsonProcessingException  {
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + initialUser.getUserId(), HttpMethod.GET, requestEntity,String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

        PersonalProfile profile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);

        Assertions.assertEquals(initialProfile.getBio(), profile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual: " + profile.getBio());
        Assertions.assertEquals(initialUser, profile.getUser(), "Expected: " + initialUser + "\nActual: " + profile.getUser());
    }

    @Test
    public void respondToUpdateProfileSuccessful() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(validProfile, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity,String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());
    
        PersonalProfile profile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
        
        Assertions.assertEquals(UserUtils.getFullName(validProfile.getUser()), UserUtils.getFullName(profile.getUser()), "Expected: "+ UserUtils.getFullName(validProfile.getUser()) + "\nActual result was " + UserUtils.getFullName(profile.getUser()));
        Assertions.assertEquals(validProfile.getBio(), profile.getBio(), "Expected: "+ validProfile.getBio() + "\nActual result was " + profile.getBio());
    }

    @Test
    public void processUpdateProfileSuccessful() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(validProfile, headers);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);
        
        PersonalProfile profile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();

        Assertions.assertEquals(UserUtils.getFullName(validProfile.getUser()), UserUtils.getFullName(profile.getUser()), "Expected: "+ UserUtils.getFullName(validProfile.getUser()) + "\nActual result was " + UserUtils.getFullName(profile.getUser()));
        Assertions.assertEquals(validProfile.getBio(), profile.getBio(), "Expected: "+ validProfile.getBio() + "\nActual result was " + profile.getBio());
    }

    @Test
    public void respondToUpdateProfileEmptyFirstName() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileEmptyFirstName, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());
    
        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("firstName", "First name must not be empty."), "Expected first name field error.");
    }
    
    @Test
    public void respondToUpdateProfileEmptyLastName() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileEmptyLastName, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("lastName", "Last name must not be empty."), "Expected last name field error.");
    }

    @Test
    public void respondToUpdateProfileFirstNameTooLong() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileFirstNameTooLong, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("firstName", "First name is too long."), "Expected first name field error.");
    }

    @Test
    public void respondToUpdateProfileLastNameTooLong() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileLastNameTooLong, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("lastName", "Last name is too long."), "Expected last name field error.");
    }

    @Test
    public void respondToUpdateProfileBioTooLong() throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileBioTooLong, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("bio", "Bio is too long."), "Expected bio field error.");
    }

    @Test
    public void processUpdateProfileInvalidProfile() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileBioTooLong, headers);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        PersonalProfile actualProfile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();
        Assertions.assertEquals(initialProfile.getBio(), actualProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + actualProfile.getBio());
        Assertions.assertEquals(UserUtils.getFullName(initialUser), UserUtils.getFullName(actualProfile.getUser()), "Expected: " + UserUtils.getFullName(initialUser) + "\nActual result was " + UserUtils.getFullName(actualProfile.getUser()));
    }
}
