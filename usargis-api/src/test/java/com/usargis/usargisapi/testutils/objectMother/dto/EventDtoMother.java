package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.EventDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EventDtoMother {
    public static EventDto.PostRequest.PostRequestBuilder postRequestSample() {
        return EventDto.PostRequest.builder()
                .name("sampleFinishedEvent name")
                .description("sampleFinishedEvent desc")
                .address("sampleFinishedEvent address")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plus(2, ChronoUnit.HOURS))
                .maxInscriptionsNumber(30)
                .isInscriptionRequired(true)
                .isInscriptionValidationRequired(true)
                .inscriptionStartDate(LocalDateTime.now().minus(30, ChronoUnit.DAYS))
                .inscriptionsEndDate(LocalDateTime.now())
                .latitude(1.2555515)
                .longitude(1.2554844);
    }
}
