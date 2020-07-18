package com.usargis.usargisapi.core.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public abstract class TeamDto {
    private TeamDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface Name {
        @Length(max = 50)
        String getName();
    }

    private interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Value
    public static class PostRequest implements Name, MissionId {
        //Fields inheriting from validation
        private String name;
        private Long missionId;
    }
}
