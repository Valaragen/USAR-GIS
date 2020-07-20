package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.InscriptionMother;
import nl.jqno.equalsverifier.EqualsVerifier;
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
    void equalsTest() {
        EqualsVerifier.forClass(Inscription.class);
    }

    @Test
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
