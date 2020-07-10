package com.usargis.usargisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Team extends ModelEntityWithLongId {
    @Column(length = 50)
    private String name;

    @ManyToOne
    private Mission mission;

    @JsonIgnoreProperties({"id.team"})
    @OneToMany(mappedBy = "id.team")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamMember> teamMembers;
}
