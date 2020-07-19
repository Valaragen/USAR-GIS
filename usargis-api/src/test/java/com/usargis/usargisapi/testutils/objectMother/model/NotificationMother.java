package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.NotificationStatus;

import java.time.LocalDateTime;

public class NotificationMother {
    public static Notification.NotificationBuilder sampleSent() {
        return Notification.builder()
                .author(UserInfoMother.sampleAuthor().build())
                .sendingDate(LocalDateTime.now())
                .status(NotificationStatus.SENT);
    }
}
