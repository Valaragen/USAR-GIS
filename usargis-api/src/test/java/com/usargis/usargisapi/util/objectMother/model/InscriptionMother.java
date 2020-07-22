package com.usargis.usargisapi.util.objectMother.model;

import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.core.model.InscriptionStatus;
import com.usargis.usargisapi.util.TestConstant;

public class InscriptionMother {
    public static Inscription.InscriptionBuilder sampleValidated() {
        return Inscription.builder()
                .userInfo(UserInfoMother.sample().build())
                .event(EventMother.sampleFinished().build())
                .inscriptionDate(TestConstant.SAMPLE_LOCAL_DATE_TIME)
                .inscriptionStatus(InscriptionStatus.VALIDATED);
    }
}
