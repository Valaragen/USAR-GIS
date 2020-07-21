package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.TeamMother;
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
    void equals_teamsWithSameContent_shouldReturnTrue() {
        Team teamToCompare = TeamMother.sample().build();

        boolean result = objectToTest.equals(teamToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentTeams_shouldReturnFalse() {
        Team teamToCompare = TeamMother.sample().build();
        teamToCompare.setId(1L);

        boolean result = objectToTest.equals(teamToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
