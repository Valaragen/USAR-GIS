package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Group extends ModelEntityWithLongId {
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "user_in_group",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<UserInfo> users;
}
