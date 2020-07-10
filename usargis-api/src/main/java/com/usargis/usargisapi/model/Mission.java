package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Mission extends ModelEntityWithLongId {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false, length = 100)
    private MissionStatus status;
    @Column(length = 5000)
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime plannedStartDate;
    private Integer expectedDurationInDays;
    private String address;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime lastEditionDate;

    @ManyToOne(optional = false)
    private UserInfo author;
}
