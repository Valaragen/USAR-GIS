package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;

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
public class Availability extends ModelEntityWithLongId {
    @ManyToOne(optional = false)
    private UserInfo userInfo;
    @ManyToOne(optional = false)
    private Mission mission;

    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
}
