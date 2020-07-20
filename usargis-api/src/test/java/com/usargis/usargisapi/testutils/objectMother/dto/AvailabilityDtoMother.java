package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.testutils.TestConstant;

public class AvailabilityDtoMother {
    public static AvailabilityDto.Create.CreateBuilder createSample() {
        return AvailabilityDto.Create.builder()
                .userInfoId("sample")
                .missionId(1L)
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }

    public static AvailabilityDto.Update.UpdateBuilder updateSample() {
        return AvailabilityDto.Update.builder()
                .startDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .endDate(TestConstant.SAMPLE_LOCAL_DATE_TIME);
    }

}
