package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Team;

public class TeamMother {
    public static Team.TeamBuilder sample() {
        return Team.builder()
                .name("sampleTeam name")
                .mission(MissionMother.sampleFinished().build());
    }

}
