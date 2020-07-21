package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface NotificationDto {

    //Interfaces to inherit hibernate validation
    interface SendingDate {
        @NotNull
        LocalDateTime getSendingDate();
    }

    @Builder
    @Value
    class PostRequest implements SendingDate {
        //Fields inheriting from validation
        private LocalDateTime sendingDate;
    }

    @Data
    class Response implements SendingDate {
        //Fields inheriting from validation
        private LocalDateTime sendingDate;

        //Fields specific to this DTO
        private Long id;
        private NotificationStatus status;
        private String authorId;
    }
}
