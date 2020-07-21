package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.UserInfoDto;

public class UserInfoDtoMother {
    public static UserInfoDto.PostRequest.PostRequestBuilder postRequestSample() {
        return UserInfoDto.PostRequest.builder()
                .id("d7c375d5-255b-4d73-9024-614af10c2c38")
                .username("sampleUser")
                .lastName("sampleUserLn")
                .firstName("sampleUserFn")
                .email("sampleUser@gmail.com")
                .emailVerified(true)
                .formattedAddress("sampleUser address")
                .phone("686957875")
                .phoneVerified(true);
    }

}
