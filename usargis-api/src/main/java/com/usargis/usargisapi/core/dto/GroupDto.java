package com.usargis.usargisapi.core.dto;

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

    @Value
    public static class PostRequest implements Name {
        //Fields inheriting from validation
        private String name;
    }


}
