package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_info_id", "event_id"})
})
public class Inscription extends ModelEntityWithLongId {
    @NotNull
    @ManyToOne(optional = false)
    private UserInfo userInfo;
    @NotNull
    @ManyToOne(optional = false)
    private Event event;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private InscriptionStatus inscriptionStatus = InscriptionStatus.WAITING_FOR_VALIDATION;

    @CreationTimestamp
    private LocalDateTime inscriptionDate;
}
