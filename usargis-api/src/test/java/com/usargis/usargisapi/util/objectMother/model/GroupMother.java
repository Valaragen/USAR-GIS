package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Group;

public class GroupMother {
    public static Group.GroupBuilder sample() {
        return Group.builder()
                .name("sampleGroup name");
    }

}
