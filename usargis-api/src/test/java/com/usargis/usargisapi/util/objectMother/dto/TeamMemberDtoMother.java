package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.TeamMemberDto;

public class TeamMemberDtoMother {
    public static TeamMemberDto.PostRequest.PostRequestBuilder postRequestSample() {
        return TeamMemberDto.PostRequest.builder()
                .teamId(1L)
                .userInfoUsername("test");
    }
}
