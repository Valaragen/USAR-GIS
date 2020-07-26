package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.util.TestConstant;

public class EventDtoMother {
    public static EventDto.EventPostRequest.EventPostRequestBuilder postRequestSample() {
        return EventDto.EventPostRequest.builder()
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
