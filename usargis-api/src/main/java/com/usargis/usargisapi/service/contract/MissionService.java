package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface MissionService extends CRUDService<Mission, Long> {
    Mission create(MissionDto.MissionPostRequest createDto);

    Mission update(Long id, MissionDto.MissionPostRequest updateDto);
}
