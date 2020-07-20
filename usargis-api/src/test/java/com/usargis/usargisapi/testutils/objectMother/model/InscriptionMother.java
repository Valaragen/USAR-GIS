package com.usargis.usargisapi.testutils.objectMother.model;

import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.core.model.InscriptionStatus;
import com.usargis.usargisapi.core.model.embeddable.InscriptionId;
import com.usargis.usargisapi.testutils.TestConstant;

import java.time.LocalDateTime;

public class InscriptionMother {
    public static Inscription.InscriptionBuilder sampleValidated() {
        return Inscription.builder()
                .id(new InscriptionId(UserInfoMother.sample().build(), EventMother.sampleFinished().build()))
                .inscriptionDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .inscriptionStatus(InscriptionStatus.VALIDATED);
    }
}
