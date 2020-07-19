package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.testutils.objectMother.dto.TeamDtoMother;
import com.usargis.usargisapi.testutils.objectMother.model.TeamMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class TeamDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class TeamPostRequestDto {
        @Test
        void teamPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            TeamDto.PostRequest teamPostRequestDto = TeamDtoMother.postRequestSample().build();

            Team team = modelMapperService.map(teamPostRequestDto, Team.class);

            Assertions.assertThat(team.getId()).isNull();
            Assertions.assertThat(team.getMission()).isNull();
            Assertions.assertThat(team.getTeamMembers()).isEmpty();
            Assertions.assertThat(team.getName()).isEqualTo(teamPostRequestDto.getName());
        }
    }

    @Nested
    class TeamResponseDto {
        @Test
        void teamResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Team team = TeamMother.sample().build();
            team.setId(1L);
            team.getMission().setId(4L);

            TeamDto.Response teamResponseDto = modelMapperService.map(team, TeamDto.Response.class);

            Assertions.assertThat(teamResponseDto.getId()).isEqualTo(team.getId());
            Assertions.assertThat(teamResponseDto.getMissionId()).isEqualTo(team.getMission().getId());
            Assertions.assertThat(teamResponseDto.getName()).isEqualTo(team.getName());
        }
    }
}
