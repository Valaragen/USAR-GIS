package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.TeamMember;
import com.usargis.usargisapi.model.embeddable.TeamMemberId;

public class TeamMemberMother {
    public static TeamMember.TeamMemberBuilder sample() {
        return TeamMember.builder()
                .id(new TeamMemberId(TeamMother.sample().build(), UserInfoMother.sample().build()))
                .isConfirmedByUser(true);
    }
}
