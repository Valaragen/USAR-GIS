package com.usargis.usargisapi.testutils.objectMother;

import com.usargis.usargisapi.model.Inscription;
import com.usargis.usargisapi.model.InscriptionStatus;
import com.usargis.usargisapi.model.embeddable.InscriptionId;

import java.time.LocalDateTime;

public class InscriptionMother {
    public static Inscription.InscriptionBuilder sampleValidated() {
        return Inscription.builder()
                .id(new InscriptionId(UserInfoMother.sample().build(), EventMother.sampleFinished().build()))
                .inscriptionDate(LocalDateTime.now())
                .inscriptionStatus(InscriptionStatus.VALIDATED);
    }
}
