package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.UserInfoDtoMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class UserInfoDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class UserInfoPostRequestDto {
        @Test
        void userInfoPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            UserInfoDto.PostRequest userInfoPostRequestDto = UserInfoDtoMother.postRequestSample().build();

            UserInfo userInfo = modelMapperService.map(userInfoPostRequestDto, UserInfo.class);

            Assertions.assertThat(userInfo.getId()).isNull();
            Assertions.assertThat(userInfo.getUsername()).isEqualTo(userInfoPostRequestDto.getUsername());
            Assertions.assertThat(userInfo.getEmail()).isEqualTo(userInfoPostRequestDto.getEmail());
            Assertions.assertThat(userInfo.getFirstName()).isEqualTo(userInfoPostRequestDto.getFirstName());
            Assertions.assertThat(userInfo.getLastName()).isEqualTo(userInfoPostRequestDto.getLastName());
            Assertions.assertThat(userInfo.getFormattedAddress()).isEqualTo(userInfoPostRequestDto.getFormattedAddress());
            Assertions.assertThat(userInfo.getPhone()).isEqualTo(userInfoPostRequestDto.getPhone());
            Assertions.assertThat(userInfo.getGroups()).isEmpty();
            Assertions.assertThat(userInfo.getInTeams()).isEmpty();
        }
    }

    @Nested
    class UserInfoResponseDto {
        @Test
        void userInfoResponseDto_mapEntityToDto_shouldMapCorrectly() {
            UserInfo userInfo = UserInfoMother.sample().build();
            userInfo.setId("test");

            UserInfoDto.Response userInfoResponseDto = modelMapperService.map(userInfo, UserInfoDto.Response.class);

            Assertions.assertThat(userInfo.getId()).isEqualTo(userInfoResponseDto.getId());
            Assertions.assertThat(userInfo.getUsername()).isEqualTo(userInfoResponseDto.getUsername());
            Assertions.assertThat(userInfo.getEmail()).isEqualTo(userInfoResponseDto.getEmail());
            Assertions.assertThat(userInfo.getFirstName()).isEqualTo(userInfoResponseDto.getFirstName());
            Assertions.assertThat(userInfo.getLastName()).isEqualTo(userInfoResponseDto.getLastName());
            Assertions.assertThat(userInfo.getFormattedAddress()).isEqualTo(userInfoResponseDto.getFormattedAddress());
            Assertions.assertThat(userInfo.getPhone()).isEqualTo(userInfoResponseDto.getPhone());
        }
    }
}
