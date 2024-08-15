package com.rev_connect_api;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.http.*;

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
    private HttpClient client;
    private ObjectMapper mapper;

    private Long testUserId = 123L;
    private String testBio = "Test bio!";

    @BeforeEach
    public void beforeEach() {
        serviceLocation = "http://localhost:" + port + "/api/profile";
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
        userRepository.deleteAll();
        profileRepository.deleteAll();

        User testUser = new User();
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
    public void respondToRetrieveProfile() {

        try {
            
            ResponseEntity<String> response = testRestTemplate.getForEntity(serviceLocation + "/" + testUserId, String.class);

            int statusCode = response.getStatusCode().value();
            Assertions.assertEquals(statusCode, 200, "Expected status code 200. Actual result was " + statusCode);

            PersonalProfile actualResult = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
            PersonalProfile expectedResult = new PersonalProfile(new User(testUserId), testBio);
            Assertions.assertEquals(expectedResult, actualResult, "Expected " + expectedResult.toString() + ". Actual result was " + actualResult.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

    @Test
    public void respondToRetrieveProfileUnauthorized() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation + "/" + testUserId))
            .GET()
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
    }

    @Test
    public void respondToUpdateProfileSuccessful() {
        String json = "{ \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            PersonalProfile profile = mapper.readValue(response.body().toString(), PersonalProfile.class);

            PersonalProfile expectedResult = new PersonalProfile(new User(testUserId), "Example bio!");
            Assertions.assertEquals(statusCode, 200, "Expected status code 200. Actual result was " + statusCode);
            Assertions.assertEquals(profile, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + profile.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

    @Test
    public void processUpdateProfileSuccessful() {
        String json = "{ \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            PersonalProfile profile = profileRepository.findById(testUserId).get();
            PersonalProfile expectedResult = new PersonalProfile(new User(testUserId), "Example bio!");
            Assertions.assertEquals(profile, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + profile.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

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
    }

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
            PersonalProfile profile = profileRepository.findById(testUserId).get();

            PersonalProfile expectedResult = new PersonalProfile(new User(testUserId), testBio);
            Assertions.assertEquals(profile, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + profile.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

    @Test
    public void respondToUpdateProfileInvalidName() {
        String json = "{ \"name\": \"\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            FieldErrorResponse responseAsFieldError = mapper.readValue(response.body().toString(), FieldErrorResponse.class);
            FieldErrorResponse expectedFieldError = new FieldErrorResponse("name", "Name must not be empty.");
            Assertions.assertEquals(statusCode, 400, "Expected status code 400. Actual result was " + statusCode);
            Assertions.assertEquals(responseAsFieldError, expectedFieldError, "Expected: " + expectedFieldError + ". Actual result was: " + responseAsFieldError);
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

    @Test
    public void processUpdateProfileInvalidName() {
        String json = "{ \"name\": \"\", \"bio\": \"Example bio!\" }";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            PersonalProfile profile = profileRepository.findById(testUserId).get();

            PersonalProfile expectedResult = new PersonalProfile(new User(testUserId), testBio);
            Assertions.assertEquals(profile, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + profile.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }
}