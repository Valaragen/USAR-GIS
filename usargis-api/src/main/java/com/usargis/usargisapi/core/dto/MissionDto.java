package com.usargis.usargisapi.core.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public abstract class MissionDto {
    private MissionDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface Name {
        @Length(min = 2, max = 100)
        String getName();
    }

    private interface Description {
        @Length(max = 5000)
        String getDescription();
    }

    private interface Address {
        @Length(max = 200)
        String getAddress();
    }

    @Value
    public static class PostRequest implements Name, Description, Address {
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
}
