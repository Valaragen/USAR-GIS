package com.usargis.usargisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Notification extends ModelEntityWithLongId {
    private LocalDateTime sendingDate;
    private NotificationStatus status;

    @ManyToOne(optional = false)
    private UserInfo author;

    @Builder.Default
    @JsonIgnoreProperties({"id.notification"})
    @OneToMany(mappedBy = "id.notification", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NotificationMessage> notificationMessages = new ArrayList<>();
}
