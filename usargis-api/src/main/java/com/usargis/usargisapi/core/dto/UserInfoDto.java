package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.InscriptionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

public interface UserInfoDto {

    @Builder
    @Value
    class PostRequest implements UserInfoDto {
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

    @Data
    class Response implements UserInfoDto {
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
