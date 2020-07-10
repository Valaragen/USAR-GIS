package com.usargis.usargisapi.model.embeddable;

import com.usargis.usargisapi.model.Notification;
import com.usargis.usargisapi.model.NotificationMessageContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NotificationMessageId implements Serializable {
    @ManyToOne
    private Notification notification;
    private NotificationMessageContentType contentType;
}
