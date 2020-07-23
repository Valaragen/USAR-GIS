package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.util.TestConstant;

public class NotificationDtoMother {
    public static NotificationDto.NotificationPostRequest.NotificationPostRequestBuilder postRequestSample() {
        return NotificationDto.NotificationPostRequest.builder()
                .sendingDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .eventId(1L)
                .missionId(2L);
    }
}
