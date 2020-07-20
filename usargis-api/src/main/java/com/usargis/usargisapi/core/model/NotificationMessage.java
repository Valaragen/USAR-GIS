package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntity;
import com.usargis.usargisapi.core.model.embeddable.NotificationMessageId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class NotificationMessage extends ModelEntity {
    @Valid
    @NotNull
    @EmbeddedId
    private NotificationMessageId id;

    @Length(min = 10, max = 10000)
    @Column(length = 10000, nullable = false)
    private String content;

    @Length(max = 100)
    @Column(length = 100)
    private String subject;

    @NotEmpty
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "notification_message_sending_modes", joinColumns = {@JoinColumn(name = "notification_message_notification_id"), @JoinColumn(name = "notification_message_notification_message_content_type")})
    @Column(name = "notification_message_sending_mode")
    private Set<NotificationMessageSendingMode> sendingModes = new HashSet<>();
}
