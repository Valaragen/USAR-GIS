package com.usargis.usargisapi.model.embeddable;

import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TeamMemberId implements Serializable {
    @ManyToOne(optional = false)
    private Team team;
    @ManyToOne(optional = false)
    private UserInfo userInfo;
}
