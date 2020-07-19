package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

public abstract class GroupDto {
    private GroupDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface Name {
        @Length(min = 1, max = 50)
        String getName();
    }

    @Builder
    @Value
    public static class PostRequest implements Name {
        //Fields inheriting from validation
        private String name;
    }

    @Data
    public static class Response implements Name {
        //Fields inheriting from validation
        private String name;

        //Fields specific to this DTO
        private Long id;
    }


}
