package com.usargis.usargisapi.core.search;

import com.usargis.usargisapi.core.model.MissionStatus;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Data
public class MissionSearchCriteria {
    private Long missionId;
    private String name;
    private MissionStatus missionStatus;
    private String description;
    private LocalDateTime afterDate;
    private LocalDateTime beforeDate;
    private String address;
    private String authorUsername;

    //Pagination and sort
    private MissionCriteriaSortBy sortBy = MissionCriteriaSortBy.DEFAULT;
    private Sort.Direction order = Sort.Direction.DESC;
    private Integer pageNo = 0;
    private Integer pageSize = 10;
}
