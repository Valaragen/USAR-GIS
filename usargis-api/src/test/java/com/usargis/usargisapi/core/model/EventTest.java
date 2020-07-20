package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.EventMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = EventMother.sampleFinished().build();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
