package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.Notification;
import com.usargis.usargisapi.model.NotificationStatus;

import java.time.LocalDateTime;

public class NotificationMother {
    public static Notification.NotificationBuilder sampleSent() {
        return Notification.builder()
                .author(UserInfoMother.sampleAuthor().build())
                .sendingDate(LocalDateTime.now())
                .status(NotificationStatus.SENT);
    }
}
