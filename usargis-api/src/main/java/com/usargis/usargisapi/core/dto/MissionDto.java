package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.MissionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public interface MissionDto {

    //Interfaces to inherit hibernate validation
    interface Name {
        @Length(min = 2, max = 100)
        String getName();
    }

    interface Description {
        @Length(max = 5000)
        String getDescription();
    }

    interface Address {
        @Length(max = 200)
        String getAddress();
    }

    @Builder
    @Value
    class MissionPostRequest implements MissionDto, Name, Description, Address {
        //Fields inheriting from validation
        private String name;
        private String description;
        private String address;

        //Fields specific to this DTO
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime plannedStartDate;
        private Integer expectedDurationInDays;
        private Double latitude;
        private Double longitude;
    }

    @Data
    class MissionResponse implements MissionDto, Name, Description, Address {
        //Fields inheriting from validation
        private String name;
        private String description;
        private String address;

        //Fields specific to this DTO
        private Long id;
        private MissionStatus status;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime plannedStartDate;
        private Integer expectedDurationInDays;
        private Double latitude;
        private Double longitude;
        private LocalDateTime creationDate;
        private LocalDateTime lastEditionDate;
        private String authorUsername;
    }
}
