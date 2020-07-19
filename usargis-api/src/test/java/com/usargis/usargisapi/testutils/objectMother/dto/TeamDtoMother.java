package com.usargis.usargisapi.testutils.objectMother.dto;

import com.usargis.usargisapi.core.dto.TeamDto;

public class TeamDtoMother {
    public static TeamDto.PostRequest.PostRequestBuilder postRequestSample() {
        return TeamDto.PostRequest.builder()
                .name("sampleTeam name")
                .missionId(1L);
    }

}
