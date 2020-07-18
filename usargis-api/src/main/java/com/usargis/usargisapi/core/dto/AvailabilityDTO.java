package com.usargis.usargisapi.core.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AvailabilityDTO {
    private String userUUID;
    private Long missionId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
