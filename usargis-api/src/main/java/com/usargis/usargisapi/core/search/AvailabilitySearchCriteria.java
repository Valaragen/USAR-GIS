package com.usargis.usargisapi.core.search;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class AvailabilitySearchCriteria {
    private String userUsername;
    private Long missionId;
    private AvailabilityCriteriaSortBy sortBy = AvailabilityCriteriaSortBy.DEFAULT;
    private Sort.Direction order = Sort.Direction.DESC;
    private Integer pageNo = 0;
    private Integer pageSize = 10;
}
