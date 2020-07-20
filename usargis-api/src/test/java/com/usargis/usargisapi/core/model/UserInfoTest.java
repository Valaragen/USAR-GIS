package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.TeamMother;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamTest {

    private Team objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = TeamMother.sample().build();
    }

    @Test
    void equalsTest() {
        EqualsVerifier.forClass(Team.class);
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
