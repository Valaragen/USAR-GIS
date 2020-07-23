package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
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
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"notification_id", "contentType"})
)
public class NotificationMessage extends ModelEntityWithLongId {

    @NotNull
    @ManyToOne(optional = false)
    private Notification notification;
    @NotNull
    @Column(nullable = false)
    private NotificationMessageContentType contentType;

    @Length(min = 10, max = 10000)
    @Column(length = 10000, nullable = false)
    private String content;

    @Length(max = 100)
    @Column(length = 100)
    private String subject;

    @NotEmpty
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "notification_message_sending_modes", joinColumns = {@JoinColumn(name = "notification_message_id")})
    @Column(name = "notification_message_sending_mode")
    private Set<NotificationMessageSendingMode> sendingModes = new HashSet<>();
}
