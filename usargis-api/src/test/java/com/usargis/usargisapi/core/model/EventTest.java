package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.EventMother;
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
    void equals_eventsWithSameContent_shouldReturnTrue() {
        Event eventToCompare = EventMother.sampleFinished().build();

        boolean result = objectToTest.equals(eventToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentEvents_shouldReturnFalse() {
        Event eventToCompare = EventMother.sampleFinished().build();
        eventToCompare.setId(1L);

        boolean result = objectToTest.equals(eventToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
