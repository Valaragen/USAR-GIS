package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface TeamMemberService extends CRUDService<TeamMember, TeamMemberId> {
    TeamMember create(TeamMemberDto.PostRequest createDto);

    TeamMember update(Long id, TeamMemberDto.PostRequest updateDto);
}
