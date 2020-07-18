package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.MissionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionDTO {
    private String name;
    private MissionStatus status;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime plannedStartDate;
    private Integer expectedDurationInDays;
    private String address;
    private Double latitude;
    private Double longitude;

    private LocalDateTime creationDate;
    private LocalDateTime lastEditionDate;

    private String authorUUID;
}
