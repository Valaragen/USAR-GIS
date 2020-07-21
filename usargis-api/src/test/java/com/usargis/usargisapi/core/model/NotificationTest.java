package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.NotificationMother;
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
    void equals_notificationsWithSameContent_shouldReturnTrue() {
        Notification notificationToCompare = NotificationMother.sampleSent().build();

        boolean result = objectToTest.equals(notificationToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentNotifications_shouldReturnFalse() {
        Notification notificationToCompare = NotificationMother.sampleSent().build();
        notificationToCompare.setId(1L);

        boolean result = objectToTest.equals(notificationToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
