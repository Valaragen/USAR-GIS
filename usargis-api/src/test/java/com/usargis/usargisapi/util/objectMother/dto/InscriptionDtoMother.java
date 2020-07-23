package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.InscriptionDto;

public class InscriptionDtoMother {
    public static InscriptionDto.InscriptionPostRequest.InscriptionPostRequestBuilder postRequestSample() {
        return InscriptionDto.InscriptionPostRequest.builder()
                .userInfoUsername("test")
                .eventId(1L);
    }
}
