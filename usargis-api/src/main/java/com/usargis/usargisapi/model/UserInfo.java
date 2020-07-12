package com.usargis.usargisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.model.common.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserInfo extends ModelEntity {
    @Id
    public String UUID;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;
    private boolean emailVerified;

    private String firstName;

    private String lastName;

    private String phone;
    private boolean phoneVerified;

    private String formattedAddress;

    @JsonIgnoreProperties({"id.userInfo"})
    @OneToMany(mappedBy = "id.userInfo", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamMember> inTeams;

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups;
}
