package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MissionMother {
    public static Mission.MissionBuilder sampleFinished() {
        return Mission.builder()
                .name("sampleFinishedMission name")
                .description("sampleFinishedMission desc")
                .address("sampleFinishedMission address")
                .creationDate(LocalDateTime.now())
                .author(UserInfoMother.sampleAuthor().build())
                .endDate(LocalDateTime.now().plus(30, ChronoUnit.DAYS))
                .expectedDurationInDays(30)
                .latitude(1.2555515)
                .longitude(1.2554844)
                .status(MissionStatus.FINISHED)
                .plannedStartDate(LocalDateTime.now());
    }
}
