package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import com.usargis.usargisapi.model.embeddable.NotificationMessageId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class NotificationMessage extends ModelEntity {
    @EmbeddedId
    private NotificationMessageId id;
    @Column(length = 10000, nullable = false)
    private String content;
    @Column(length = 100)
    private String subject;

    @ElementCollection
    @CollectionTable(name = "notification_message_sending_modes", joinColumns = {@JoinColumn(name = "notification_message_notification_id"), @JoinColumn(name = "notification_message_notification_message_content_type")})
    @Column(name = "notification_message_sending_mode")
    private Set<NotificationMessageSendingMode> sendingModes = new HashSet<>();
}
