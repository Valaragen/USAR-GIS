package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.Group;

public class GroupMother {
    public static Group.GroupBuilder sample() {
        return Group.builder()
                .name("sampleGroup name");
    }

}
