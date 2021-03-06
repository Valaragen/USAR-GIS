package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.AvailabilityMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvailabilityTest {

    private Availability objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = AvailabilityMother.sample().build();
    }

    @Test
    void equals_availabilitiesWithSameContent_shouldReturnTrue() {
        Availability availabilityToCompare = AvailabilityMother.sample().build();

        boolean result = objectToTest.equals(availabilityToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentAvailabilities_shouldReturnFalse() {
        Availability availabilityToCompare = AvailabilityMother.sample().build();
        availabilityToCompare.setId(1L);

        boolean result = objectToTest.equals(availabilityToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }
}
