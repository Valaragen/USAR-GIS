package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;
import com.usargis.usargisapi.util.objectMother.model.TeamMemberMother;
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
    void equals_teamMembersWithSameContent_shouldReturnTrue() {
        TeamMember teamMemberToCompare = TeamMemberMother.sample().build();

        boolean result = objectToTest.equals(teamMemberToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentTeamMembers_shouldReturnFalse() {
        TeamMember teamMemberToCompare = TeamMemberMother.sample().build();
        teamMemberToCompare.setId(new TeamMemberId());

        boolean result = objectToTest.equals(teamMemberToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
