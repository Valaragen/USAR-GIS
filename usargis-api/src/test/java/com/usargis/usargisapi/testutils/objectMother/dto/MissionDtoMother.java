package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.MissionDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MissionDtoMother {
    public static MissionDto.PostRequest.PostRequestBuilder postRequestSample() {
        return MissionDto.PostRequest.builder()
                .name("sampleFinishedMission name")
                .description("sampleFinishedMission desc")
                .address("sampleFinishedMission address")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plus(30, ChronoUnit.DAYS))
                .expectedDurationInDays(30)
                .latitude(1.2555515)
                .longitude(1.2554844)
                .plannedStartDate(LocalDateTime.now());
    }
}
