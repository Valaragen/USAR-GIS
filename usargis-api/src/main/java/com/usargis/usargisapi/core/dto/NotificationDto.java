package com.usargis.usargisapi.core.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class NotificationDto {
    private NotificationDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface SendingDate {
        @NotNull
        LocalDateTime getSendingDate();
    }

    @Value
    public static class PostRequest implements SendingDate {
        //Fields inheriting from validation
        private LocalDateTime sendingDate;
    }


}
