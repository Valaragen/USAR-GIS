package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

public interface UserInfoDto {

    @Builder
    @Data
    class UserInfoPostRequest implements UserInfoDto {
        private String username;
        private String email;
        private boolean emailVerified;
        private String firstName;
        private String lastName;
        private String phone;
        private boolean phoneVerified;
        private String formattedAddress;

    }

    @Data
    class UserInfoResponse implements UserInfoDto {
        private String id;
        private String username;
        private String email;
        private boolean emailVerified;
        private String firstName;
        private String lastName;
        private String phone;
        private boolean phoneVerified;
        private String formattedAddress;
    }
}
