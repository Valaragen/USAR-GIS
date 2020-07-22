package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "user_info_id"})
})
public class TeamMember extends ModelEntityWithLongId {

    @NotNull
    @ManyToOne(optional = false)
    private Team team;
    @NotNull
    @ManyToOne(optional = false)
    private UserInfo userInfo;

    private boolean isConfirmedByUser;
}
