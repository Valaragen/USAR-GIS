package com.usargis.usargisapi.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

public interface GroupDto {

    //Interfaces to inherit hibernate validation
    interface Name {
        @Length(min = 1, max = 50)
        String getName();
    }

    @Builder
    @Data
    class GroupPostRequest implements GroupDto, Name {
        //Fields inheriting from validation
        private String name;
    }

    @Data
    class GroupResponse implements GroupDto, Name {
        //Fields inheriting from validation
        private String name;

        //Fields specific to this DTO
        private Long id;
    }
}
