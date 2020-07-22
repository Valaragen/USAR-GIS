package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.objectMother.model.InscriptionMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscriptionTest {

    private Inscription objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = InscriptionMother.sampleValidated().build();
    }

    @Test
    void equals_inscriptionsWithSameContent_shouldReturnTrue() {
        Inscription inscriptionToCompare = InscriptionMother.sampleValidated().build();

        boolean result = objectToTest.equals(inscriptionToCompare);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void equals_differentInscriptions_shouldReturnFalse() {
        Inscription inscriptionToCompare = InscriptionMother.sampleValidated().build();
        inscriptionToCompare.setId(1L);

        boolean result = objectToTest.equals(inscriptionToCompare);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
