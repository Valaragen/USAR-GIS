package com.usargis.usargisapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Notification extends ModelEntityWithLongId {
    @NotNull
    @Column(nullable = false)
    private LocalDateTime sendingDate;

    @NotNull
    @Column(nullable = false)
    private NotificationStatus status;

    @NotNull
    @ManyToOne(optional = false)
    private UserInfo author;

    @Builder.Default
    @JsonIgnoreProperties({"id.notification"})
    @OneToMany(mappedBy = "id.notification", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NotificationMessage> notificationMessages = new ArrayList<>();
}
