package com.usargis.usargisapi.core.search;

import com.usargis.usargisapi.core.model.MissionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionSearchCriteria {
    private Long missionId;
    private String name;
    private MissionStatus missionStatus;
    private String authorUsername;
    private LocalDateTime afterDate;
    private LocalDateTime beforeDate;
}
