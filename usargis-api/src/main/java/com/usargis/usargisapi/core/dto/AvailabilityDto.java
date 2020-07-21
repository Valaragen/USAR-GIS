package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface AvailabilityDto {
    //Interfaces to inherit hibernate validation
    interface UserInfoId {
        @NotBlank
        String getUserInfoId();
    }

    interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Builder
    @Value
    class Create implements UserInfoId, MissionId, AvailabilityDto {
        //Fields inheriting from validation
        String userInfoId;
        Long missionId;

        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Builder
    @Value
    class Update implements AvailabilityDto {
        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Data
    class Response implements UserInfoId, MissionId, AvailabilityDto {
        //Fields inheriting from validation
        String userInfoId;
        Long missionId;

        //Fields specific to this DTO
        Long id;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }
}
