package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.InscriptionDto;

public class InscriptionDtoMother {
    public static InscriptionDto.PostRequest.PostRequestBuilder postRequestSample() {
        return InscriptionDto.PostRequest.builder()
                .userInfoUsername("test")
                .eventId(1L);
    }
}
