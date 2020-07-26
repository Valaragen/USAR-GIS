package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.NotificationStatus;
import com.usargis.usargisapi.util.TestConstant;

public class NotificationMother {
    public static Notification.NotificationBuilder sampleSent() {
        return Notification.builder()
                .author(UserInfoMother.sampleAuthor().build())
                .mission(MissionMother.sampleFinished().build())
                .event(EventMother.sampleFinished().build())
                .sendingDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .status(NotificationStatus.SENT);
    }
}
