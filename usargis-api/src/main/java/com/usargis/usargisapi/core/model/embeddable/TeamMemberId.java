package com.usargis.usargisapi.core.model.embeddable;

import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.core.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TeamMemberId implements Serializable {
    @NotNull
    @ManyToOne(optional = false)
    private Team team;
    @NotNull
    @ManyToOne(optional = false)
    private UserInfo userInfo;
}
