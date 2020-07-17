package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.NotificationMessage;
import com.usargis.usargisapi.model.NotificationMessageContentType;
import com.usargis.usargisapi.model.NotificationMessageSendingMode;
import com.usargis.usargisapi.model.embeddable.NotificationMessageId;

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
