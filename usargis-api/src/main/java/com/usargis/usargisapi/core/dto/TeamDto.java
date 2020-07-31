package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public interface TeamDto {

    //Interfaces to inherit hibernate validation
    interface Name {
        @Length(max = 50)
        String getName();
    }

    interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Builder
    @Data
    class TeamPostRequest implements TeamDto, Name, MissionId {
        //Fields inheriting from validation
        private String name;
        private Long missionId;
    }

    @Data
    class TeamResponse implements TeamDto, Name, MissionId {
        //Fields inheriting from validation
        private String name;
        private Long missionId;

        //Fields specific to this DTO
        private Long id;
    }
}
