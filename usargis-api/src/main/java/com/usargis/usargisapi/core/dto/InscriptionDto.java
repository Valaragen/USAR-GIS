package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.InscriptionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

public interface InscriptionDto {

    @Builder
    @Value
    class PostRequest implements InscriptionDto {
        //Fields specific to this DTO
        private Long eventId;
        private String userInfoId;
    }

    @Data
    class Response implements InscriptionDto {
        //Fields specific to this DTO
        private Long eventId;
        private String userInfoId;
        private InscriptionStatus inscriptionStatus;
        private LocalDateTime inscriptionDate;
    }
}
