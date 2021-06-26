package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.UserInfo;

public class UserInfoMother {
    public static UserInfo.UserInfoBuilder sample() {
        return UserInfo.builder()
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

    public static UserInfo.UserInfoBuilder sample2() {
        return UserInfo.builder()
                .id("z7c596v5-255b-4d73-9024-614cd10c2c38")
                .username("sampleUser2")
                .lastName("sampleUser2Ln")
                .firstName("sampleUser2Fn")
                .email("sampleUser2@gmail.com")
                .emailVerified(true)
                .formattedAddress("sampleUser address")
                .phone("684586235")
                .phoneVerified(true);
    }

    public static UserInfo.UserInfoBuilder sampleAuthor() {
        return UserInfo.builder()
                .id("8f99352c-b3c6-4bdf-8278-b1614c40fda8")
                .username("sampleAuthor")
                .lastName("sampleAuthorLn")
                .firstName("sampleAuthorFn")
                .email("sampleAuthor@gmail.com")
                .emailVerified(true)
                .formattedAddress("sampleAuthor address")
                .phone("686955858")
                .phoneVerified(true);
    }
}
