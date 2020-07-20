package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.TeamMemberMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamMemberTest {

    private TeamMember objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = TeamMemberMother.sample().build();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
