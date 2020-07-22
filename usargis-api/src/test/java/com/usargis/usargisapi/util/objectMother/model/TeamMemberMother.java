package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.TeamMember;

public class TeamMemberMother {
    public static TeamMember.TeamMemberBuilder sample() {
        return TeamMember.builder()
                .team(TeamMother.sample().build())
                .userInfo(UserInfoMother.sample().build())
                .isConfirmedByUser(true);
    }
}
