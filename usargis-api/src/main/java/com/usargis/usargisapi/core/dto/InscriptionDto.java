package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.InscriptionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

public interface InscriptionDto {

    @Builder
    @Value
    class InscriptionPostRequest implements InscriptionDto {
        //Fields specific to this DTO
        private Long eventId;
        private String userInfoUsername;
    }

    @Data
    class InscriptionResponse implements InscriptionDto {
        //Fields specific to this DTO
        private Long id;
        private Long eventId;
        private String userInfoUsername;
        private InscriptionStatus inscriptionStatus;
        private LocalDateTime inscriptionDate;
    }
}
