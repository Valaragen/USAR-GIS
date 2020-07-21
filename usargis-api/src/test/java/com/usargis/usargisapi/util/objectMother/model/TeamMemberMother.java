package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;

public class TeamMemberMother {
    public static TeamMember.TeamMemberBuilder sample() {
        return TeamMember.builder()
                .id(new TeamMemberId(TeamMother.sample().build(), UserInfoMother.sample().build()))
                .isConfirmedByUser(true);
    }
}
