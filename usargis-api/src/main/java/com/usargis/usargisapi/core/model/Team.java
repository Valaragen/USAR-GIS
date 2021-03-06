package com.usargis.usargisapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.core.model.common.ModelEntityWithLongId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Team extends ModelEntityWithLongId {
    @Length(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    private Mission mission;

    @Builder.Default
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamMember> teamMembers = new ArrayList<>();
}
