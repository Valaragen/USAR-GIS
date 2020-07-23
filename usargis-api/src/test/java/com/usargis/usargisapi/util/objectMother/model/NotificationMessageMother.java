package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;

import java.util.Collections;
import java.util.HashSet;

public class NotificationMessageMother {
    public static NotificationMessage.NotificationMessageBuilder sample() {
        return NotificationMessage.builder()
                .notification(NotificationMother.sampleSent().build())
                .contentType(NotificationMessageContentType.TEXT)
                .content("sample content")
                .subject("sample subject")
                .sendingModes(new HashSet<>(Collections.singletonList(NotificationMessageSendingMode.MAIL)));
    }
}
