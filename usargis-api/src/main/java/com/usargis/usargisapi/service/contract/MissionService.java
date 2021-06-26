package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.search.MissionSearchCriteria;
import com.usargis.usargisapi.service.contract.common.CRUDService;

import java.util.List;

public interface MissionService extends CRUDService<Mission, Long> {
    List<Mission> searchAll(MissionSearchCriteria missionSearchCriteria);

    Mission create(MissionDto.MissionPostRequest createDto);

    Mission update(Long id, MissionDto.MissionPostRequest updateDto);
}
