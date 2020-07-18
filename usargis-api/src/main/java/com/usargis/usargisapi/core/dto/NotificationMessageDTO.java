package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationMessageSendingMode;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class NotificationMessageDTO {
    private String content;
    private String subject;

    private Set<NotificationMessageSendingMode> sendingModes = new HashSet<>();
}
