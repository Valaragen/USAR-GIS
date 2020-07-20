package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AvailabilityMother {
    public static Availability.AvailabilityBuilder sample() {
        return Availability.builder()
                .mission(MissionMother.sampleFinished().build())
                .userInfo(UserInfoMother.sample().build())
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }
}
