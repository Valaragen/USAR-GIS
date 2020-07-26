package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.UserInfoDto;

public class UserInfoDtoMother {
    public static UserInfoDto.UserInfoPostRequest.UserInfoPostRequestBuilder postRequestSample() {
        return UserInfoDto.UserInfoPostRequest.builder()
                .username("sampleuser")
                .lastName("sampleUserLn")
                .firstName("sampleUserFn")
                .email("sampleuser@gmail.com")
                .emailVerified(true)
                .formattedAddress("sampleUser address")
                .phone("686957875")
                .phoneVerified(true);
    }

}
