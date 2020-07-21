package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.embeddable.NotificationMessageId;
import com.usargis.usargisapi.util.objectMother.model.NotificationMessageMother;
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
    void equals_notificationMessagesWithSameContent_shouldReturnTrue() {
        NotificationMessage notificationMessageToCompare = NotificationMessageMother.sample().build();

        boolean result = objectToTest.equals(notificationMessageToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentNotificationMessages_shouldReturnFalse() {
        NotificationMessage notificationMessageToCompare = NotificationMessageMother.sample().build();
        notificationMessageToCompare.setId(new NotificationMessageId());

        boolean result = objectToTest.equals(notificationMessageToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
