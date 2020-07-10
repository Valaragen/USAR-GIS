package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Availability extends ModelEntityWithLongId {
    @ManyToOne
    private UserInfo userInfo;
    @ManyToOne
    private Mission mission;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
