package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.MissionMother;
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
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
