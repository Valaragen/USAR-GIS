package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;

import java.util.Collections;
import java.util.HashSet;

public class NotificationMessageDtoMother {
    public static NotificationMessageDto.PostRequest.PostRequestBuilder postRequestSample() {
        return NotificationMessageDto.PostRequest.builder()
                .notificationId(1L)
                .contentType(NotificationMessageContentType.TEXT)
                .content("sample content")
                .subject("sample subject")
                .sendingModes(new HashSet<>(Collections.singletonList(NotificationMessageSendingMode.MAIL)));
    }
}
