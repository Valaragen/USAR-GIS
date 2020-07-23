package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AvailabilityController implements ApiRestController {

    private AvailabilityService availabilityService;
    private ModelMapperService modelMapperService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService, ModelMapperService modelMapperService) {
        this.availabilityService = availabilityService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<List<AvailabilityDto.Response>> searchForAvailabilities(@Valid AvailabilitySearch availabilitySearch) {
        List<Availability> availabilities = availabilityService.searchAll(availabilitySearch);
        if (availabilities.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_AVAILABILITY_FOUND);
        }
        return new ResponseEntity<>(availabilities.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<AvailabilityDto.Response> getAvailabilityById(@PathVariable Long id) {
        Optional<Availability> availabilityOptional = availabilityService.findById(id);
        Availability availability = availabilityOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.OK);
    }

    @PostMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<AvailabilityDto.Response> createNewAvailability(@RequestBody @Valid AvailabilityDto.Create availabilityCreateDto) {
        Availability availability = availabilityService.create(availabilityCreateDto);
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.CREATED);
    }

    @PutMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<AvailabilityDto.Response> updateAvailability(@PathVariable Long id, @RequestBody @Valid AvailabilityDto.Update updateDto) {
        Availability availability = availabilityService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.OK);
    }

    @DeleteMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteAvailability(@PathVariable Long id) {
        availabilityService.delete(availabilityService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id))));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private AvailabilityDto.Response convertToResponseDto(Availability availability) {
        return modelMapperService.map(availability, AvailabilityDto.Response.class);
    }

}
