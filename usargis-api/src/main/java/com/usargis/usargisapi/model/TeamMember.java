package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import com.usargis.usargisapi.model.embeddable.TeamMemberId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class TeamMember extends ModelEntity {
    @EmbeddedId
    private TeamMemberId id;

    private boolean isConfirmedByUser;
}
