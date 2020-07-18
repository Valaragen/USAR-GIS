package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.InscriptionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InscriptionDto {
    private InscriptionStatus inscriptionStatus;
    private LocalDateTime inscriptionDate;
}
