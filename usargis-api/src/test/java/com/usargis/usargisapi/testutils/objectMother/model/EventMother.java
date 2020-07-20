package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.EventStatus;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EventMother {
    public static Event.EventBuilder sampleFinished() {
        return Event.builder()
                .name("sampleFinishedEvent name")
                .description("sampleFinishedEvent desc")
                .address("sampleFinishedEvent address")
                .creationDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .author(UserInfoMother.sampleAuthor().build())
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .status(EventStatus.FINISHED)
                .maxInscriptionsNumber(30)
                .isInscriptionRequired(true)
                .isInscriptionValidationRequired(true)
                .inscriptionStartDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .inscriptionsEndDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .latitude(1.2555515)
                .longitude(1.2554844);
    }
}
