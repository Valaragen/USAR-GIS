package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.util.TestConstant;

public class AvailabilityDtoMother {
    public static AvailabilityDto.AvailabilityCreate.AvailabilityCreateBuilder createSample() {
        return AvailabilityDto.AvailabilityCreate.builder()
                .userInfoUsername("sample")
                .missionId(1L)
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }

    public static AvailabilityDto.AvailabilityUpdate.AvailabilityUpdateBuilder updateSample() {
        return AvailabilityDto.AvailabilityUpdate.builder()
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }

}
