package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public abstract class NotificationMessageDto {
    private NotificationMessageDto() {
    }

    //Interfaces to inherit hibernate validation
    private interface Content {
        @Length(min = 10, max = 10000)
        String getContent();
    }

    private interface Subject {
        @Length(max = 100)
        String getSubject();
    }

    @Builder
    @Data
    public static class PostRequest implements Content, Subject {
        //Fields inheriting from validation
        private String content;
        private String subject;
        private NotificationMessageContentType contentType;

        //Fields specific to this DTO
        private Set<NotificationMessageSendingMode> sendingModes;
    }

    @Data
    public static class Response implements Content, Subject {
        //Fields inheriting from validation
        private String content;
        private String subject;

        //Fields specific to this DTO
        private Long notificationId;
        private NotificationMessageContentType contentType;
        private Set<NotificationMessageSendingMode> sendingModes;
    }
}
