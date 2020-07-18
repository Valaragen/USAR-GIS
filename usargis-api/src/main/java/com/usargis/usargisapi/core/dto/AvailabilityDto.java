package com.usargis.usargisapi.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public abstract class AvailabilityDto {
    private AvailabilityDto() {
    }

    //Interfaces to inherit the validator
    public interface id {
        @Positive
        Long getId();
    }
    public interface userUuid {
        @NotBlank
        String getUserUuid();
    }
    public interface missionId {
        @NotNull
        Long getMissionId();
    }

    @Value
    public static class Create implements userUuid, missionId {
        String userUuid;
        Long missionId;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

    @Value
    public static class Response implements id, userUuid, missionId {
        Long id;
        String userUuid;
        Long missionId;
        LocalDateTime startDate;
        LocalDateTime endDate;
    }

}
