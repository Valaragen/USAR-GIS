package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.NotificationMother;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTest {

    private Notification objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = NotificationMother.sampleSent().build();
    }

    @Test
    void equalsTest() {
        EqualsVerifier.forClass(Notification.class);
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
