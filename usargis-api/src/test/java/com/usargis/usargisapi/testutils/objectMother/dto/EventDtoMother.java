package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EventDtoMother {
    public static EventDto.PostRequest.PostRequestBuilder postRequestSample() {
        return EventDto.PostRequest.builder()
                .name("sampleFinishedEvent name")
                .description("sampleFinishedEvent desc")
                .address("sampleFinishedEvent address")
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .maxInscriptionsNumber(30)
                .isInscriptionRequired(true)
                .isInscriptionValidationRequired(true)
                .inscriptionStartDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .inscriptionsEndDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .latitude(1.2555515)
                .longitude(1.2554844);
    }
}
