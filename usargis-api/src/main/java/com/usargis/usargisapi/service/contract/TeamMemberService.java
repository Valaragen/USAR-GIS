package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface TeamMemberService extends CRUDService<TeamMember, Long> {
    TeamMember create(TeamMemberDto.TeamMemberPostRequest createDto);

    TeamMember update(Long id, TeamMemberDto.TeamMemberPostRequest updateDto);
}
