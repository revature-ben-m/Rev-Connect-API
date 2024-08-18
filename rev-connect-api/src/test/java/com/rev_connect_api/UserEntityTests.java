package com.rev_connect_api;
import static org.assertj.core.api.Assertions.assertThat;
import com.rev_connect_api.entity.User;
import org.junit.Test;

public class UserEntityTests {

    private User referenceUser = new User(1, "Krishna", "krishna123");

    @Test
    public void testUserEquality() {
        // Check if two users with the same details are the same
        User testUser1 = new User(4, "om", "Om12345");
        User testUser2 = new User(4, "om", "Om12345");

        assertThat(testUser1).isEqualTo(testUser2);

        // Check if two users with different details are different
        User testUser3 = new User(5, "Dev", "Dev12345");

        assertThat(testUser1).isNotEqualTo(testUser3);
    }

    @Test
    public void testCreateUser() {
        // Create a new User instance and that the details are correct
        User User1 = new User(2, "Hari", "Hair123");
        User1.setEmail("hari123@revature.net");

        // Assertions
        assertThat(User1).isNotNull();
        assertThat(User1).isNotEqualTo(referenceUser);
        assertThat(User1.getAccountId()).isEqualTo(2);
        assertThat(User1.getUsername()).isEqualTo("Hari");
        assertThat(User1.getPassword()).isEqualTo("Hair123");
        assertThat(User1.getEmail()).isEqualTo("hari123@revature.net");
    }

    @Test
    public void testSetAndGetUserFields() {
        // Create an empty User instance and set values using setters
        User User2 = new User();
        User2.setAccountId(3);
        User2.setUsername("Sitar");
        User2.setPassword("Sitar123");
        User2.setEmail("sitar@revature.com");

        // Assertions
        assertThat(User2).isNotNull();
        assertThat(User2).isNotEqualTo(referenceUser);
        assertThat(User2.getAccountId()).isEqualTo(3);
        assertThat(User2.getUsername()).isEqualTo("Sitar");
        assertThat(User2.getPassword()).isEqualTo("Sitar123");
        assertThat(User2.getEmail()).isEqualTo("sitar@revature.com");
    }


    @Test
    public void testCorrectStringRepresentation() {
        // Check if the user details are displayed correctly as a string
        User testUser = new User(6, "Raj", "Raj456");

        String expectedString = "User{accountId=6, username='Raj', password='Raj456'}";
        assertThat(testUser.toString()).isEqualTo(expectedString);
    }
}
