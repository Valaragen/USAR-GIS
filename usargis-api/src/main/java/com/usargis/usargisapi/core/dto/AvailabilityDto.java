package com.usargis.usargisapi.core.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class AvailabilityDto {
    private AvailabilityDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface UserUuid {
        @NotBlank
        String getUserUuid();
    }

    private interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Value
    public static class Create implements UserUuid, MissionId {
        //Fields inheriting from validation
        String userUuid;
        Long missionId;

        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Value
    public static class Update {
        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Data
    public static class Response implements UserUuid, MissionId {
        //Fields inheriting from validation
        String userUuid;
        Long missionId;

        //Fields specific to this DTO
        Long id;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

}
