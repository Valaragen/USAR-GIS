package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.NotificationStatus;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;

public class NotificationMother {
    public static Notification.NotificationBuilder sampleSent() {
        return Notification.builder()
                .author(UserInfoMother.sampleAuthor().build())
                .sendingDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .status(NotificationStatus.SENT);
    }
}
