package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.util.TestConstant;

public class AvailabilityMother {
    public static Availability.AvailabilityBuilder sample() {
        return Availability.builder()
                .mission(MissionMother.sampleTeamEngagement().build())
                .userInfo(UserInfoMother.sample().build())
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }
}
