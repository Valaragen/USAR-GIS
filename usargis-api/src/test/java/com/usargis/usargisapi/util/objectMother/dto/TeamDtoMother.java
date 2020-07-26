package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.TeamDto;

public class TeamDtoMother {
    public static TeamDto.TeamPostRequest.TeamPostRequestBuilder postRequestSample() {
        return TeamDto.TeamPostRequest.builder()
                .name("sampleTeam name")
                .missionId(1L);
    }

}
