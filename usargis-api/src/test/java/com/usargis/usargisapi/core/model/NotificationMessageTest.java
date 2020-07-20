package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.NotificationMessageMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationMessageTest {

    private NotificationMessage objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = NotificationMessageMother.sample().build();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
