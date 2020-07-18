package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

class AvailabilityDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void checkMapping() {
        AvailabilityDto.Create availabilityDto = new AvailabilityDto.Create(null, null, LocalDateTime.now(), LocalDateTime.now());
        Availability availability = modelMapper.map(availabilityDto, Availability.class);

        Assertions.assertThat(availability.getMission()).isNull();
        Assertions.assertThat(availability.getUserInfo()).isNull();
        Assertions.assertThat(availability.getEndDate()).isEqualTo(availabilityDto.getEndDate());
        Assertions.assertThat(availability.getStartDate()).isEqualTo(availabilityDto.getStartDate());
    }

    @Test
    void checkAvailabilityCreateDtoValidation() {
        AvailabilityDto.Create availabilityDto = new AvailabilityDto.Create(null, null, LocalDateTime.now(), LocalDateTime.now());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AvailabilityDto.Create>> violations = validator.validate(availabilityDto);

        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    void checkAvailabilityResponseDtoValidation() {
        AvailabilityDto.Response availabilityDto = new AvailabilityDto.Response(null, null, null, null, null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AvailabilityDto.Response>> violations = validator.validate(availabilityDto);

        Assertions.assertThat(violations).hasSize(2);
    }

}
