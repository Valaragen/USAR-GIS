package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface AvailabilityDto {
    //Interfaces to inherit hibernate validation
    interface UserInfoUsername {
        @NotBlank
        String getUserInfoUsername();
    }

    interface MissionId {
        @NotNull
        Long getMissionId();
    }

    @Builder
    @Data
    class AvailabilityCreate implements UserInfoUsername, MissionId, AvailabilityDto {
        //Fields inheriting from validation
        String userInfoUsername;
        Long missionId;

        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Builder
    @Data
    class AvailabilityUpdate implements AvailabilityDto {
        //Fields specific to this DTO
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Data
    class AvailabilityResponse implements AvailabilityDto, UserInfoUsername, MissionId {
        //Fields inheriting from validation
        String userInfoUsername;
        Long missionId;

        //Fields specific to this DTO
        Long id;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }
}
