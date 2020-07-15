package com.usargis.usargisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.model.common.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserInfo extends ModelEntity {
    @Id
    private String UUID;

    @Column(nullable = false, unique = true)
    private String username;

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
    private List<TeamMember> inTeams = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();
}
