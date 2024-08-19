package com.rev_connect_api;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.ProfileRepository;
import com.rev_connect_api.repositories.UserRepository;

//TODO: Authentication in methods
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NameAndBioTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;

    @LocalServerPort
    private int port;

    private String serviceLocation;
    private ObjectMapper mapper;
    
    private User testUser;
    private String testBio = "Test bio!";

    @BeforeEach
    public void beforeEach() {
        serviceLocation = "http://localhost:" + port + "/api/profile";
        mapper = new ObjectMapper();
        profileRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        PersonalProfile testProfile = new PersonalProfile(testUser, testBio);
        userRepository.save(testUser);
        profileRepository.save(testProfile);
    }

    @Test
    public void myTest() {
        System.out.println("Running myTest");
        Assertions.assertEquals(userRepository.count(), 1);
        Assertions.assertEquals(userRepository.count(), 1);
        User testUser = new User();
        PersonalProfile testProfile = new PersonalProfile(testUser, testBio);
        userRepository.save(testUser);
        profileRepository.save(testProfile);
        Assertions.assertEquals(userRepository.count(), 2);
        Assertions.assertEquals(profileRepository.count(), 2);

        System.out.println(userRepository.findAll());
    }

    @Test
    public void respondToRetrieveProfile() throws JsonMappingException, JsonProcessingException  {
        ResponseEntity<String> response = testRestTemplate.getForEntity(serviceLocation + "/" + testUser.getId(), String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(statusCode, 200, "Expected status code 200. Actual result was " + statusCode);

        

        PersonalProfile actualResult = mapper.readValue(response.getBody().toString(), PersonalProfile.class);

        Assertions.assertEquals(testBio, actualResult.getBio(), "Expected: " + testBio + "\nActual: " + actualResult.getBio());
        Assertions.assertEquals(testUser, actualResult.getUser(), "Expected: " + testUser + "\nActual: " + actualResult.getUser());
    }
    
    /*
    @Test
    public void respondToRetrieveProfileUnauthorized() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation + "/" + testUser.getId()))
            .GET()
            .header("Content-Type", "application/json")
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            Assertions.assertEquals(401, statusCode, "Expected status code 401. Actual result was " + statusCode);
            Assertions.assertEquals(response.body().toString(), "", "Expected response body to be empty. Actual response body: " + response.body().toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    } */

    @Test
    public void respondToUpdateProfileSuccessful() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getId());
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(200, statusCode, "Expected status code 200. Actual result was " + statusCode);
    
        PersonalProfile profile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
        
        Assertions.assertEquals("Doe, John", profile.getUser().fullName(), "Expected: Doe, John\nActual result was " + profile.getUser().fullName());
        Assertions.assertEquals("Example bio!", profile.getBio(), "Expected: Example bio!\nActual result was " + profile.getBio());
    }

    @Test
    public void processUpdateProfileSuccessful() {
        User newUser = new User(testUser.getId());
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);
        
        PersonalProfile profile = profileRepository.findById(testUser.getId()).get();

        Assertions.assertEquals("Example bio!", profile.getBio(), "Expected: Example bio!\nActual result was " + profile.getBio());
        Assertions.assertEquals("Doe, John", profile.getUser().fullName(), "Expected: Doe, John\nActual result was " + profile.getUser().fullName());
    }

    /*
    @Test
    public void respondToUpdateProfileUnauthorized() {
        String json = "{ \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            int statusCode = response.statusCode();
            Assertions.assertEquals(statusCode, 401, "Expected status code 401. Actual result was " + statusCode);
            Assertions.assertEquals(response.body().toString(), "", "Expected response body to be empty. Actual response body: " + response.body().toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }  */

    /*
    @Test
    public void processUpdateProfileUnauthorized() {
        String json = "{ \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            PersonalProfile profile = profileRepository.findById(testUser.getId()).get();

            PersonalProfile expectedResult = new PersonalProfile(new User(testUser.getId()), testBio);
            Assertions.assertEquals(profile, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + profile.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }  */

    @Test
    public void respondToUpdateProfileInvalidUser() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(-1L);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(404, statusCode, "Expected status code 404. Actual result was " + statusCode);
    
        Assertions.assertEquals("User -1 was not found.", response.getBody());
    }

    @Test
    public void processUpdateProfileInvalidUser() {
        User newUser = new User(-1L);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        PersonalProfile actualProfile = profileRepository.findByUser_UId(testUser.getId()).get();
        Assertions.assertEquals(testBio, actualProfile.getBio(), "Expected: " + testBio + "\nActual result was " + actualProfile.getBio());
        Assertions.assertEquals(testUser.fullName(), actualProfile.getUser().fullName(), "Expected: " + testUser.fullName() + "\nActual result was " + actualProfile.getUser().fullName());
    }

    @Test
    public void respondToUpdateProfileInvalidFirstName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getId());
        newUser.setFirstName("");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);
    
        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("firstName", "First name must not be empty."), "Expected first name field error.");
    }
    
    @Test
    public void respondToUpdateProfileInvalidLastName() throws JsonMappingException, JsonProcessingException {
        User newUser = new User(testUser.getId());
        newUser.setFirstName("John");
        newUser.setLastName("");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        int statusCode = response.getStatusCode().value();
        Assertions.assertEquals(400, statusCode, "Expected status code 400. Actual result was " + statusCode);

        FieldErrorResponse resp = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
        Assertions.assertEquals(resp, new FieldErrorResponse("lastName", "Last name must not be empty."), "Expected last name field error.");
    }

    @Test
    public void processUpdateProfileInvalidName() {
        User newUser = new User(testUser.getId());
        newUser.setFirstName("");
        newUser.setLastName("Doe");
        PersonalProfile newProfile = new PersonalProfile(newUser, "Example bio!");
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(newProfile);
        testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

        PersonalProfile actualProfile = profileRepository.findByUser_UId(testUser.getId()).get();
        Assertions.assertEquals(testBio, actualProfile.getBio(), "Expected: " + testBio + "\nActual result was " + actualProfile.getBio());
        Assertions.assertEquals(testUser.fullName(), actualProfile.getUser().fullName(), "Expected: " + testUser.fullName() + "\nActual result was " + actualProfile.getUser().fullName());
    }
}