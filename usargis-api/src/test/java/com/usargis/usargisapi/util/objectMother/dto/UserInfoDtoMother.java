package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.UserInfoDto;

public class UserInfoDtoMother {
    public static UserInfoDto.PostRequest.PostRequestBuilder postRequestSample() {
        return UserInfoDto.PostRequest.builder()
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
