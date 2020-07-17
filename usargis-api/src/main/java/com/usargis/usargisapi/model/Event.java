package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Event extends ModelEntityWithLongId {
    @Column(length = 100)
    private String name;
    @Column(nullable = false)
    private EventStatus status;
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
    private String address;

    @ManyToOne(optional = false)
    private UserInfo author;

}
