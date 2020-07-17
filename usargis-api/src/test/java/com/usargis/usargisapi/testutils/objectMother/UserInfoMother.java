package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.UserInfo;

public class UserInfoMother {
    public static UserInfo.UserInfoBuilder complete() {
        return UserInfo.builder()
                .UUID("d7c375d5-255b-4d73-9024-614af10c2c38")
                .username("user")
                .lastName("user")
                .firstName("user")
                .email("usertest@gmail.com")
                .emailVerified(true)
                .formattedAddress("5 test address")
                .phone("686957875")
                .phoneVerified(true);
    }
}
