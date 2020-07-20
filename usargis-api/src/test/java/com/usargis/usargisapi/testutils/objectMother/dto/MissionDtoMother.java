package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.testutils.TestConstant;

public class MissionDtoMother {
    public static MissionDto.PostRequest.PostRequestBuilder postRequestSample() {
        return MissionDto.PostRequest.builder()
                .name("sampleFinishedMission name")
                .description("sampleFinishedMission desc")
                .address("sampleFinishedMission address")
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .expectedDurationInDays(30)
                .latitude(1.2555515)
                .longitude(1.2554844)
                .plannedStartDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }
}
