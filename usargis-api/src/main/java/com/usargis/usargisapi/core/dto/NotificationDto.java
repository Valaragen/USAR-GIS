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
    class NotificationPostRequest implements NotificationDto, SendingDate {
        //Fields inheriting from validation
        private LocalDateTime sendingDate;
        private Long missionId;
        private Long eventId;
    }

    @Data
    class NotificationResponse implements NotificationDto, SendingDate {
        //Fields inheriting from validation
        private LocalDateTime sendingDate;

        //Fields specific to this DTO
        private Long id;
        private Long missionId;
        private Long eventId;
        private NotificationStatus status;
        private String authorUsername;
    }
}
