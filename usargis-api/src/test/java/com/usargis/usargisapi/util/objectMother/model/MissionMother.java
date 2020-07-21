package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.util.TestConstant;

public class MissionMother {
    public static Mission.MissionBuilder sampleFinished() {
        return Mission.builder()
                .name("sampleFinishedMission name")
                .description("sampleFinishedMission desc")
                .address("sampleFinishedMission address")
                .creationDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .author(UserInfoMother.sampleAuthor().build())
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .expectedDurationInDays(30)
                .latitude(1.2555515)
                .longitude(1.2554844)
                .status(MissionStatus.FINISHED)
                .plannedStartDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }
}
