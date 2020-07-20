package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.GroupMother;
import nl.jqno.equalsverifier.EqualsVerifier;
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
    void equalsTest() {
        EqualsVerifier.forClass(Group.class);
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
