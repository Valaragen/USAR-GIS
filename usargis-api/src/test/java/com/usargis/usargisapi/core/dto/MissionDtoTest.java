package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.testutils.objectMother.dto.MissionDtoMother;
import com.usargis.usargisapi.testutils.objectMother.model.MissionMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class MissionDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class MissionPostRequestDto {
        @Test
        void missionPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            MissionDto.PostRequest missionPostRequestDto = MissionDtoMother.postRequestSample().build();

            Mission mission = modelMapperService.map(missionPostRequestDto, Mission.class);

            Assertions.assertThat(mission.getId()).isNull();
            Assertions.assertThat(mission.getCreationDate()).isNull();
            Assertions.assertThat(mission.getLastEditionDate()).isNull();
            Assertions.assertThat(mission.getAuthor()).isNull();
            Assertions.assertThat(mission.getName()).isEqualTo(missionPostRequestDto.getName());
            Assertions.assertThat(mission.getDescription()).isEqualTo(missionPostRequestDto.getDescription());
            Assertions.assertThat(mission.getAddress()).isEqualTo(missionPostRequestDto.getAddress());
            Assertions.assertThat(mission.getStartDate()).isEqualTo(missionPostRequestDto.getStartDate());
            Assertions.assertThat(mission.getEndDate()).isEqualTo(missionPostRequestDto.getEndDate());
            Assertions.assertThat(mission.getPlannedStartDate()).isEqualTo(missionPostRequestDto.getPlannedStartDate());
            Assertions.assertThat(mission.getExpectedDurationInDays()).isEqualTo(missionPostRequestDto.getExpectedDurationInDays());
            Assertions.assertThat(mission.getLatitude()).isEqualTo(missionPostRequestDto.getLatitude());
            Assertions.assertThat(mission.getLongitude()).isEqualTo(missionPostRequestDto.getLongitude());
        }
    }

    @Nested
    class MissionResponseDto {
        @Test
        void missionResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Mission mission = MissionMother.sampleFinished().build();
            mission.setId(1L);
            mission.getAuthor().setId("test");

            MissionDto.Response missionResponseDto = modelMapperService.map(mission, MissionDto.Response.class);

            Assertions.assertThat(missionResponseDto.getId()).isEqualTo(mission.getId());
            Assertions.assertThat(missionResponseDto.getName()).isEqualTo(mission.getName());
            Assertions.assertThat(missionResponseDto.getDescription()).isEqualTo(mission.getDescription());
            Assertions.assertThat(missionResponseDto.getAddress()).isEqualTo(mission.getAddress());
            Assertions.assertThat(missionResponseDto.getStatus()).isEqualTo(mission.getStatus());
            Assertions.assertThat(missionResponseDto.getStartDate()).isEqualTo(mission.getStartDate());
            Assertions.assertThat(missionResponseDto.getEndDate()).isEqualTo(mission.getEndDate());
            Assertions.assertThat(missionResponseDto.getPlannedStartDate()).isEqualTo(mission.getPlannedStartDate());
            Assertions.assertThat(missionResponseDto.getExpectedDurationInDays()).isEqualTo(mission.getExpectedDurationInDays());
            Assertions.assertThat(missionResponseDto.getLatitude()).isEqualTo(mission.getLatitude());
            Assertions.assertThat(missionResponseDto.getLongitude()).isEqualTo(mission.getLongitude());
            Assertions.assertThat(missionResponseDto.getCreationDate()).isEqualTo(mission.getCreationDate());
            Assertions.assertThat(missionResponseDto.getLastEditionDate()).isEqualTo(mission.getLastEditionDate());
            Assertions.assertThat(missionResponseDto.getAuthorId()).isEqualTo(mission.getAuthor().getId());
        }
    }
}
