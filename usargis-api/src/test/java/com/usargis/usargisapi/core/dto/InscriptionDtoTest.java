package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.InscriptionDtoMother;
import com.usargis.usargisapi.util.objectMother.model.InscriptionMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class InscriptionDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class InscriptionPostRequestDto {
        @Test
        void inscriptionPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            InscriptionDto.InscriptionPostRequest inscriptionPostRequestDto = InscriptionDtoMother.postRequestSample().build();

            Inscription inscription = modelMapperService.map(inscriptionPostRequestDto, Inscription.class);

            Assertions.assertThat(inscription.getId()).isNull();
            Assertions.assertThat(inscription.getEvent()).isNull();
            Assertions.assertThat(inscription.getUserInfo()).isNull();
            Assertions.assertThat(inscription.getInscriptionDate()).isNull();
        }
    }

    @Nested
    class InscriptionResponseDto {
        @Test
        void inscriptionResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Inscription inscription = InscriptionMother.sampleValidated().build();
            inscription.setId(1L);
            inscription.getUserInfo().setId("test");
            inscription.getEvent().setId(2L);

            InscriptionDto.InscriptionResponse inscriptionResponseDto = modelMapperService.map(inscription, InscriptionDto.InscriptionResponse.class);

            Assertions.assertThat(inscriptionResponseDto.getId()).isEqualTo(inscription.getId());
            Assertions.assertThat(inscriptionResponseDto.getEventId()).isEqualTo(inscription.getEvent().getId());
            Assertions.assertThat(inscriptionResponseDto.getUserInfoUsername()).isEqualTo(inscription.getUserInfo().getUsername());
            Assertions.assertThat(inscriptionResponseDto.getInscriptionDate()).isEqualTo(inscription.getInscriptionDate());
            Assertions.assertThat(inscriptionResponseDto.getInscriptionStatus()).isEqualTo(inscription.getInscriptionStatus());
        }
    }
}
