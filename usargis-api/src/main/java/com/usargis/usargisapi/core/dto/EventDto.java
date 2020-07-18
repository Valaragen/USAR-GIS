package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String name;
    private EventStatus status;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxInscriptionsNumber;
    private boolean isInscriptionRequired;
    private boolean isInscriptionValidationRequired;
    private LocalDateTime inscriptionStartDate;
    private LocalDateTime inscriptionsEndDate;
    private Double latitude;
    private Double longitude;

    private LocalDateTime creationDate;
    private LocalDateTime lastEditionDate;
    private String address;

    private String authorUUID;

}
