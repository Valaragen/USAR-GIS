package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.testutils.objectMother.dto.AvailabilityDtoMother;
import com.usargis.usargisapi.testutils.objectMother.model.AvailabilityMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class AvailabilityDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class AvailabilityCreateDto {
        @Test
        void availabilityCreateDto_mapDtoToEntity_shouldMapCorrectly() {
            AvailabilityDto.Create availabilityCreateDto = AvailabilityDtoMother.createSample().build();

            Availability availability = modelMapperService.map(availabilityCreateDto, Availability.class);

            Assertions.assertThat(availability.getId()).isNull();
            Assertions.assertThat(availability.getMission()).isNull();
            Assertions.assertThat(availability.getUserInfo()).isNull();
            Assertions.assertThat(availability.getEndDate()).isEqualTo(availabilityCreateDto.getEndDate());
            Assertions.assertThat(availability.getStartDate()).isEqualTo(availabilityCreateDto.getStartDate());
        }
    }

    @Nested
    class AvailabilityUpdateDto {
        @Test
        void availabilityUpdateDto_mapDtoToEntity_shouldMapCorrectly() {
            AvailabilityDto.Update availabilityUpdateDto = AvailabilityDtoMother.updateSample().build();

            Availability availability = modelMapperService.map(availabilityUpdateDto, Availability.class);

            Assertions.assertThat(availability.getId()).isNull();
            Assertions.assertThat(availability.getMission()).isNull();
            Assertions.assertThat(availability.getUserInfo()).isNull();
            Assertions.assertThat(availability.getEndDate()).isEqualTo(availabilityUpdateDto.getEndDate());
            Assertions.assertThat(availability.getStartDate()).isEqualTo(availabilityUpdateDto.getStartDate());
        }
    }

    @Nested
    class AvailabilityResponseDto {
        @Test
        void availabilityResponseDto_mapEntityToDto_shouldMapCorrectly() {
            Availability availability = AvailabilityMother.sample().build();
            availability.setId(1L);
            availability.getMission().setId(4L);

            AvailabilityDto.Response availabilityResponseDto = modelMapperService.map(availability, AvailabilityDto.Response.class);

            Assertions.assertThat(availabilityResponseDto.getEndDate()).isEqualTo(availability.getEndDate());
            Assertions.assertThat(availabilityResponseDto.getStartDate()).isEqualTo(availability.getStartDate());
            Assertions.assertThat(availabilityResponseDto.getId()).isEqualTo(availability.getId());
            Assertions.assertThat(availabilityResponseDto.getMissionId()).isEqualTo(availability.getMission().getId());
        }
    }
}
