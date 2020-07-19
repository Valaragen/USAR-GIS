package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class AvailabilityDto {
    private AvailabilityDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface UserInfoId {
        @NotBlank
        String getUserInfoId();
    }

    private interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Builder
    @Value
    public static class Create implements UserInfoId, MissionId {
        //Fields inheriting from validation
        String userInfoId;
        Long missionId;

        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Builder
    @Value
    public static class Update {
        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Data
    public static class Response implements UserInfoId, MissionId {
        //Fields inheriting from validation
        String userInfoId;
        Long missionId;

        //Fields specific to this DTO
        Long id;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

}
