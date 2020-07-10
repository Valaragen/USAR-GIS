package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserInfo extends ModelEntity {
    @Id
    public String uuid;

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

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups;
}
