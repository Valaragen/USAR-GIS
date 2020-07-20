package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.UserInfoMother;
import nl.jqno.equalsverifier.EqualsVerifier;
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
    void equalsTest() {
        EqualsVerifier.forClass(UserInfo.class);
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
