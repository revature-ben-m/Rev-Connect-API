package com.rev_connect_api;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@SpringBootTest
public class NameAndBioTest {

    private String serviceLocation = "http://localhost:8080/api";

    @Autowired
    UserRepository userRepository;

    @Test
    public void myTest() {
        System.out.println("Running myTest");
        Assertions.assertEquals(userRepository.count(), 0);
        userRepository.save(new User("2", "Bill", "Bill bio"));
        Assertions.assertEquals(userRepository.count(), 1);
    }

    @Test
    public void updateBioSuccessful() {
        String json = "{ \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        String uId = "1";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation + "/profile/" + uId))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(response.body().toString(), User.class);

            User expectedResult = new User(uId, "John Doe", "Example bio!");
            Assertions.assertEquals(statusCode, 200, "Expected status code 200. Actual result was " + statusCode);
            Assertions.assertEquals(user, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + user.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }

    @Test
    public void updateBioUnauthorized() {
        String json = "{ \"token\": \"abcd1234\", \"name\": \"John Doe\", \"bio\": \"Example bio!\" }";
        String uId = "1";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceLocation + "/profile/" + uId))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(response.body().toString(), User.class);

            User expectedResult = new User(uId, "John Doe", "Example bio!");
            Assertions.assertEquals(statusCode, 401, "Expected status code 401. Actual result was " + statusCode);
            Assertions.assertEquals(user, expectedResult, "Expected " + expectedResult.toString() + ". Actual result was " + user.toString());
        } catch (Exception e) {
            Assertions.fail("Caught exception when sending: " + e.getMessage());
        }
    }
}