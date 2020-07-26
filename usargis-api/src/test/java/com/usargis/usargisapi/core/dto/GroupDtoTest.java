package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.GroupDtoMother;
import com.usargis.usargisapi.util.objectMother.model.GroupMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class GroupDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class GroupPostRequestDto {
        @Test
        void groupPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            GroupDto.GroupPostRequest groupPostRequestDto = GroupDtoMother.postRequestSample().build();

            Group group = modelMapperService.map(groupPostRequestDto, Group.class);

            Assertions.assertThat(group.getId()).isNull();
            Assertions.assertThat(group.getUsers()).isEmpty();
            Assertions.assertThat(group.getName()).isEqualTo(groupPostRequestDto.getName());
        }
    }

    @Nested
    class GroupResponseDto {
        @Test
        void groupResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Group group = GroupMother.sample().build();
            group.setId(1L);

            GroupDto.GroupResponse groupResponseDto = modelMapperService.map(group, GroupDto.GroupResponse.class);

            Assertions.assertThat(groupResponseDto.getId()).isEqualTo(group.getId());
            Assertions.assertThat(groupResponseDto.getName()).isEqualTo(group.getName());
        }
    }
}
