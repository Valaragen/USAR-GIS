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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Mission extends ModelEntityWithLongId {
    @Length(min = 2, max = 100)
    @Column(length = 100, nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private MissionStatus status;
    @Length(max = 5000)
    @Column(length = 5000)
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime plannedStartDate;
    private Integer expectedDurationInDays;
    @Length(max = 200)
    private String address;
    private Double latitude;
    private Double longitude;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime lastEditionDate;

    @NotNull
    @ManyToOne(optional = false)
    private UserInfo author;
}
