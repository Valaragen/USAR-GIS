package com.usargis.usargisapi.core.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private String username;

    private String email;
    private boolean emailVerified;

    private String firstName;

    private String lastName;

    private String phone;
    private boolean phoneVerified;

    private String formattedAddress;

    private void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    private void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
