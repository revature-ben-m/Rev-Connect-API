package com.rev_connect_api.models;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BusinessProfileTests {

    private BusinessProfile noArgsTest = new BusinessProfile();
    private BusinessProfile noIDArgTest = new BusinessProfile(123, "This profile has no ID");
    private BusinessProfile allArgTest = new BusinessProfile(999, 123, "This profile has all Args");

    @Test
    void noArgsConstructorTestCreatesProfile() {
        assertThat(noArgsTest).isNotNull();
    }

    @Test
    void noIdArgConstructorTestCreatesProfile() {
        assertThat(noIDArgTest).isNotNull();
    }

    @Test
    void AllArgConstructorTestCreatesProfile() {
        assertThat(allArgTest).isNotNull();
    }

    @Test
    void newProfilesAreCreated() {
        BusinessProfile allArgTestTwo = new BusinessProfile((long)9999, 1234, "This profile has all Args");
        assertThat(allArgTestTwo).isNotNull();
        assertThat(allArgTestTwo).isNotEqualTo(allArgTest);
    }

    @Test
    void gettersGetCorrectValue() {
        assertEquals(allArgTest.getId(), 999);
        assertEquals(allArgTest.getUserId(), 123);
        assertEquals(allArgTest.getBioText(), "This profile has all Args");
    }

    @Test
    void settersSetValue() {
        BusinessProfile blank = new BusinessProfile();
        blank.setId(5555);
        blank.setUserId(100);
        blank.setBioText("Setters are working!");

        assertThat(blank.getId()).isNotNull().isEqualTo(5555);
        assertThat(blank.getUserId()).isNotNull().isEqualTo(100);
        assertThat(blank.getBioText()).isNotBlank().isEqualTo("Setters are working!");
    }

}