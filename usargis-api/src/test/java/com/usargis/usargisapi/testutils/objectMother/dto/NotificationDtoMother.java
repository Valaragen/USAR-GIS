package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.NotificationDto;

import java.time.LocalDateTime;

public class NotificationDtoMother {
    public static NotificationDto.PostRequest.PostRequestBuilder postRequestSample() {
        return NotificationDto.PostRequest.builder()
                .sendingDate(LocalDateTime.now());
    }
}
