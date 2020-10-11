package com.usargis.usargisapi.service.contract;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface MissionService extends CRUDService<Mission, Long> {
    Mission create(MissionDto.MissionPostRequest createDto);

    Mission update(Long id, MissionDto.MissionPostRequest updateDto);

    Mission patch(Long id, JsonPatch patchDocument) throws JsonPatchException;
}
