package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.testutils.objectMother.model.InscriptionMother;
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
    void toString_shouldWork() {
        Assertions.assertThat(objectToTest.toString()).isNotBlank();
    }

}
