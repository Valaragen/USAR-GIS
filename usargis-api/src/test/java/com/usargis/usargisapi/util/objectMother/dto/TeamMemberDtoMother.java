package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.TeamMemberDto;

public class TeamMemberDtoMother {
    public static TeamMemberDto.TeamMemberPostRequest.TeamMemberPostRequestBuilder postRequestSample() {
        return TeamMemberDto.TeamMemberPostRequest.builder()
                .teamId(1L)
                .userInfoUsername("test");
    }
}
