package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.Availability;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AvailabilityMother {
    public static Availability.AvailabilityBuilder sample() {
        return Availability.builder()
                .mission(MissionMother.sampleFinished().build())
                .userInfo(UserInfoMother.sample().build())
                .startDate(LocalDateTime.now().minus(1, ChronoUnit.MONTHS))
                .endDate(LocalDateTime.now());
    }
}
