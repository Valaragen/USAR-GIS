package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface TeamService extends CRUDService<Team, Long> {
    Team create(TeamDto.TeamPostRequest createDto);

    Team update(Long id, TeamDto.TeamPostRequest updateDto);
}
