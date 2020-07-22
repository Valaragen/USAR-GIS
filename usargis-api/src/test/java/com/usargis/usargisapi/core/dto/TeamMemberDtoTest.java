package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.TeamMemberDtoMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMemberMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class TeamMemberDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class TeamMemberPostRequestDto {
        @Test
        void teamMemberPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            TeamMemberDto.PostRequest teamMemberPostRequestDto = TeamMemberDtoMother.postRequestSample().build();

            TeamMember teamMember = modelMapperService.map(teamMemberPostRequestDto, TeamMember.class);

            Assertions.assertThat(teamMember.getId()).isNull();
            Assertions.assertThat(teamMember.getTeam()).isNull();
            Assertions.assertThat(teamMember.getUserInfo()).isNull();
        }
    }

    @Nested
    class TeamMemberResponseDto {
        @Test
        void teamMemberResponseDto_mapEntityToDto_shouldMapCorrectly() {
            TeamMember teamMember = TeamMemberMother.sample().build();
            teamMember.setId(1L);
            teamMember.getTeam().setId(2L);
            teamMember.getUserInfo().setId("test");

            TeamMemberDto.Response teamMemberResponseDto = modelMapperService.map(teamMember, TeamMemberDto.Response.class);

            Assertions.assertThat(teamMemberResponseDto.getId()).isEqualTo(teamMember.getId());
            Assertions.assertThat(teamMemberResponseDto.getTeamId()).isEqualTo(teamMember.getTeam().getId());
            Assertions.assertThat(teamMemberResponseDto.getUserInfoId()).isEqualTo(teamMember.getUserInfo().getId());
            Assertions.assertThat(teamMemberResponseDto.isConfirmedByUser()).isEqualTo(teamMember.isConfirmedByUser());
        }
    }
}
