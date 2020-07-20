package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;

public class NotificationDtoMother {
    public static NotificationDto.PostRequest.PostRequestBuilder postRequestSample() {
        return NotificationDto.PostRequest.builder()
                .sendingDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }
}
