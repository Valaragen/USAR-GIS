package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.InscriptionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

public interface TeamMemberDto {

    @Builder
    @Value
    class PostRequest implements TeamMemberDto {
        //Fields specific to this DTO
        private Long teamId;
        private String userInfoId;
    }

    @Data
    class Response implements TeamMemberDto {
        //Fields specific to this DTO
        private Long id;
        private Long teamId;
        private String userInfoId;
        private InscriptionStatus inscriptionStatus;
        private LocalDateTime inscriptionDate;
    }
}
