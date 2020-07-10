package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import com.usargis.usargisapi.model.embeddable.TeamMemberId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class TeamMember extends ModelEntity {
    @EmbeddedId
    private TeamMemberId id;

    private boolean isConfirmedByUser;
}
