package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

public interface TeamMemberDto {

    @Builder
    @Value
    class PostRequest implements TeamMemberDto {
        //Fields specific to this DTO
        private Long teamId;
        private String userInfoUsername;
    }

    @Data
    class Response implements TeamMemberDto {
        //Fields specific to this DTO
        private Long id;
        private Long teamId;
        private String userInfoUsername;
        private boolean isConfirmedByUser;
    }
}
