package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.NotificationStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationDTO {
    private LocalDateTime sendingDate;
    private NotificationStatus status;

    private String authorUUID;

    private List<NotificationMessage> notificationMessages = new ArrayList<>();
}
