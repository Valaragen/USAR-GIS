package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.core.model.Team;

public class TeamMother {
    public static Team.TeamBuilder sample() {
        return Team.builder()
                .name("sampleTeam name")
                .mission(MissionMother.sampleFinished().build());
    }

}
