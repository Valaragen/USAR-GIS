package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInfoTest {

    private UserInfo objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = UserInfoMother.sample().build();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

    @Test
    void equals_userInfosWithSameContent_shouldReturnTrue() {
        UserInfo userInfoToCompare = UserInfoMother.sample().build();

        boolean result = objectToTest.equals(userInfoToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentUserInfos_shouldReturnFalse() {
        UserInfo userInfoToCompare = UserInfoMother.sample().build();
        userInfoToCompare.setId("test");

        boolean result = objectToTest.equals(userInfoToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void setUsername_givenString_shouldLowerIt() {
        String username = "UsERnAme";

        objectToTest.setUsername(username);

        Assertions.assertThat(objectToTest.getUsername()).isEqualTo(username.toLowerCase());
    }

    @Test
    void setEmail_givenString_shouldLowerIt() {
        String email = "EmAil@gmail.COM";

        objectToTest.setEmail(email);

        Assertions.assertThat(objectToTest.getEmail()).isEqualTo(email.toLowerCase());
    }

}
