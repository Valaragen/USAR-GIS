package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public interface NotificationMessageDto {

    //Interfaces to inherit hibernate validation
    interface Content {
        @Length(min = 10, max = 10000)
        String getContent();
    }

    interface Subject {
        @Length(max = 100)
        String getSubject();
    }

    @Builder
    @Data
    class PostRequest implements NotificationMessageDto, Content, Subject {
        //Fields inheriting from validation
        private String content;
        private String subject;

        //Fields specific to this DTO
        private Long notificationId;
        private NotificationMessageContentType contentType;
        private Set<NotificationMessageSendingMode> sendingModes;
    }

    @Data
    class Response implements NotificationMessageDto, Content, Subject {
        //Fields inheriting from validation
        private String content;
        private String subject;

        //Fields specific to this DTO
        private Long id;
        private Long notificationId;
        private NotificationMessageContentType contentType;
        private Set<NotificationMessageSendingMode> sendingModes;
    }
}
