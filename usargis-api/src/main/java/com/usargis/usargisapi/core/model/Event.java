package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Event extends ModelEntityWithLongId {
    @Length(min = 2, max = 100)
    @Column(length = 100)
    private String name;
    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private EventStatus status = EventStatus.PENDING;
    @Length(max = 5000)
    @Column(length = 5000)
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxInscriptionsNumber;
    private boolean isInscriptionRequired;
    private boolean isInscriptionValidationRequired;
    private LocalDateTime inscriptionStartDate;
    private LocalDateTime inscriptionsEndDate;

    private Double latitude;
    private Double longitude;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime lastEditionDate;
    @Length(max = 200)
    private String address;

    @NotNull
    @ManyToOne(optional = false)
    private UserInfo author;

}
