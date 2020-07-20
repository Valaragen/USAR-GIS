package com.usargis.usargisapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usargis.usargisapi.core.model.common.ModelEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
//@Setter(AccessLevel.PRIVATE)
@Entity
public class UserInfo extends ModelEntity {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private boolean emailVerified;

    private String firstName;

    private String lastName;

    private String phone;
    private boolean phoneVerified;

    private String formattedAddress;

    @Builder.Default
    @JsonIgnoreProperties({"id.userInfo"})
    @OneToMany(mappedBy = "id.userInfo", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamMember> inTeams = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Group> groups = new HashSet<>();

    private void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    private void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
