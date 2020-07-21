package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionTest {

    private Mission objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = MissionMother.sampleFinished().build();
    }

    @Test
    void equals_missionsWithSameContent_shouldReturnTrue() {
        Mission missionToCompare = MissionMother.sampleFinished().build();

        boolean result = objectToTest.equals(missionToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentMissions_shouldReturnFalse() {
        Mission missionToCompare = MissionMother.sampleFinished().build();
        missionToCompare.setId(1L);

        boolean result = objectToTest.equals(missionToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
