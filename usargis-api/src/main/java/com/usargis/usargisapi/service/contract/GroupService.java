package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface GroupService extends CRUDService<Group, Long> {
    Group create(GroupDto.GroupPostRequest createDto);

    Group update(Long id, GroupDto.GroupPostRequest updateDto);
}
