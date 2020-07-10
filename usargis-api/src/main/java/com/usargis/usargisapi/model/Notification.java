package com.usargis.usargisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Notification extends ModelEntityWithLongId {
    private LocalDateTime sendingDate;
    private NotificationStatus status;

    @ManyToOne
    private UserInfo userInfo;

    @JsonIgnoreProperties({"id.notification"})
    @OneToMany(mappedBy = "id.notification")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NotificationMessage> notificationMessages;
}
