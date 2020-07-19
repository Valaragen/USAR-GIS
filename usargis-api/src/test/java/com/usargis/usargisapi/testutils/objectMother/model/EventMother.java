package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.EventStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EventMother {
    public static Event.EventBuilder sampleFinished() {
        return Event.builder()
                .name("sampleFinishedEvent name")
                .description("sampleFinishedEvent desc")
                .address("sampleFinishedEvent address")
                .creationDate(LocalDateTime.now())
                .author(UserInfoMother.sampleAuthor().build())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plus(2, ChronoUnit.HOURS))
                .status(EventStatus.FINISHED)
                .maxInscriptionsNumber(30)
                .isInscriptionRequired(true)
                .isInscriptionValidationRequired(true)
                .inscriptionStartDate(LocalDateTime.now().minus(30, ChronoUnit.DAYS))
                .inscriptionsEndDate(LocalDateTime.now())
                .latitude(1.2555515)
                .longitude(1.2554844);
    }
}
