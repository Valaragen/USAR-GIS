package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;
import com.usargis.usargisapi.core.model.embeddable.NotificationMessageId;

import java.util.Collections;
import java.util.HashSet;

public class NotificationMessageMother {
    public static NotificationMessage.NotificationMessageBuilder sample() {
        return NotificationMessage.builder()
                .id(new NotificationMessageId(NotificationMother.sampleSent().build(), NotificationMessageContentType.TEXT))
                .content("sample content")
                .subject("sample subject")
                .sendingModes(new HashSet<>(Collections.singletonList(NotificationMessageSendingMode.MAIL)));
    }
}
