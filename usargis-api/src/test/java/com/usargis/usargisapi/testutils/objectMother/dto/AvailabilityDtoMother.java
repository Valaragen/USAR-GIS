package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.AvailabilityDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AvailabilityDtoMother {
    public static AvailabilityDto.Create.CreateBuilder createSample() {
        return AvailabilityDto.Create.builder()
                .userInfoId("sample")
                .missionId(1L)
                .startDate(LocalDateTime.now().minus(1, ChronoUnit.MONTHS))
                .endDate(LocalDateTime.now());
    }

    public static AvailabilityDto.Update.UpdateBuilder updateSample() {
        return AvailabilityDto.Update.builder()
                .startDate(LocalDateTime.now().minus(1, ChronoUnit.MONTHS))
                .endDate(LocalDateTime.now());
    }

}
