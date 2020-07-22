package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.EventDtoMother;
import com.usargis.usargisapi.util.objectMother.model.EventMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class EventDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class EventPostRequestDto {
        @Test
        void eventPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            EventDto.PostRequest eventPostRequestDto = EventDtoMother.postRequestSample().build();

            Event event = modelMapperService.map(eventPostRequestDto, Event.class);

            Assertions.assertThat(event.getId()).isNull();
            Assertions.assertThat(event.getCreationDate()).isNull();
            Assertions.assertThat(event.getLastEditionDate()).isNull();
            Assertions.assertThat(event.getAuthor()).isNull();
            Assertions.assertThat(event.getName()).isEqualTo(eventPostRequestDto.getName());
            Assertions.assertThat(event.getDescription()).isEqualTo(eventPostRequestDto.getDescription());
            Assertions.assertThat(event.getAddress()).isEqualTo(eventPostRequestDto.getAddress());
            Assertions.assertThat(event.getStartDate()).isEqualTo(eventPostRequestDto.getStartDate());
            Assertions.assertThat(event.getEndDate()).isEqualTo(eventPostRequestDto.getEndDate());
            Assertions.assertThat(event.getMaxInscriptionsNumber()).isEqualTo(eventPostRequestDto.getMaxInscriptionsNumber());
            Assertions.assertThat(event.isInscriptionRequired()).isEqualTo(eventPostRequestDto.isInscriptionRequired());
            Assertions.assertThat(event.isInscriptionValidationRequired()).isEqualTo(eventPostRequestDto.isInscriptionValidationRequired());
            Assertions.assertThat(event.getInscriptionStartDate()).isEqualTo(eventPostRequestDto.getInscriptionStartDate());
            Assertions.assertThat(event.getInscriptionsEndDate()).isEqualTo(eventPostRequestDto.getInscriptionsEndDate());
            Assertions.assertThat(event.getLatitude()).isEqualTo(eventPostRequestDto.getLatitude());
            Assertions.assertThat(event.getLongitude()).isEqualTo(eventPostRequestDto.getLongitude());
        }
    }

    @Nested
    class EventResponseDto {
        @Test
        void eventResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Event event = EventMother.sampleFinished().build();
            event.setId(1L);
            event.getAuthor().setId("test");

            EventDto.Response eventResponseDto = modelMapperService.map(event, EventDto.Response.class);

            Assertions.assertThat(eventResponseDto.getId()).isEqualTo(event.getId());
            Assertions.assertThat(eventResponseDto.getName()).isEqualTo(event.getName());
            Assertions.assertThat(eventResponseDto.getDescription()).isEqualTo(event.getDescription());
            Assertions.assertThat(eventResponseDto.getAddress()).isEqualTo(event.getAddress());
            Assertions.assertThat(eventResponseDto.getStartDate()).isEqualTo(event.getStartDate());
            Assertions.assertThat(eventResponseDto.getEndDate()).isEqualTo(event.getEndDate());
            Assertions.assertThat(eventResponseDto.getMaxInscriptionsNumber()).isEqualTo(event.getMaxInscriptionsNumber());
            Assertions.assertThat(eventResponseDto.isInscriptionRequired()).isEqualTo(event.isInscriptionRequired());
            Assertions.assertThat(eventResponseDto.isInscriptionValidationRequired()).isEqualTo(event.isInscriptionValidationRequired());
            Assertions.assertThat(eventResponseDto.getInscriptionStartDate()).isEqualTo(event.getInscriptionStartDate());
            Assertions.assertThat(eventResponseDto.getInscriptionsEndDate()).isEqualTo(event.getInscriptionsEndDate());
            Assertions.assertThat(eventResponseDto.getLatitude()).isEqualTo(event.getLatitude());
            Assertions.assertThat(eventResponseDto.getLongitude()).isEqualTo(event.getLongitude());
            Assertions.assertThat(eventResponseDto.getAuthorUsername()).isEqualTo(event.getAuthor().getUsername());
            Assertions.assertThat(eventResponseDto.getStatus()).isEqualTo(event.getStatus());
            Assertions.assertThat(eventResponseDto.getCreationDate()).isEqualTo(event.getCreationDate());
            Assertions.assertThat(eventResponseDto.getLastEditionDate()).isEqualTo(event.getLastEditionDate());
        }
    }
}
