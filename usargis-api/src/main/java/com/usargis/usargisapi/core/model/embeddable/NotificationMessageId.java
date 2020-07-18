package com.usargis.usargisapi.core.model.embeddable;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NotificationMessageId implements Serializable {
    @NotNull
    @ManyToOne(optional = false)
    private Notification notification;
    @NotNull
    @Column(nullable = false)
    private NotificationMessageContentType contentType;
}
