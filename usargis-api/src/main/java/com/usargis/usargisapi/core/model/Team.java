package com.usargis.usargisapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Team extends ModelEntityWithLongId {
    @Column(length = 50)
    private String name;

    @ManyToOne(optional = false)
    private Mission mission;

    @Builder.Default
    @JsonIgnoreProperties({"id.team"})
    @OneToMany(mappedBy = "id.team", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamMember> teamMembers = new ArrayList<>();
}
