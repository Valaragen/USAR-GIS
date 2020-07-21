package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.GroupMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupTest {

    private Group objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = GroupMother.sample().build();
    }

    @Test
    void equals_groupsWithSameContent_shouldReturnTrue() {
        Group groupToCompare = GroupMother.sample().build();

        boolean result = objectToTest.equals(groupToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentGroups_shouldReturnFalse() {
        Group groupToCompare = GroupMother.sample().build();
        groupToCompare.setId(1L);

        boolean result = objectToTest.equals(groupToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
