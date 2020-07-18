package com.usargis.usargisapi.testutils.objectMother;

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
                .latitude(1.2555515)
                .longitude(1.2554844)
                .status(EventStatus.FINISHED)
                .inscriptionStartDate(LocalDateTime.now().minus(30, ChronoUnit.DAYS))
                .inscriptionsEndDate(LocalDateTime.now())
                .isInscriptionRequired(true);
    }
}
