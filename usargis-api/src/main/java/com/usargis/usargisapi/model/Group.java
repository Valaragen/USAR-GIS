package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntityWithLongId;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "_group")
public class Group extends ModelEntityWithLongId {
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_in_group",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<UserInfo> users = new HashSet<>();
}
