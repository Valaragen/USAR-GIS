package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntity;
import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class TeamMember extends ModelEntity {
    @Valid
    @NotNull
    @EmbeddedId
    private TeamMemberId id;
    
    private boolean isConfirmedByUser;
}
