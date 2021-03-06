package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private SecurityService securityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService, ModelMapperService modelMapperService,
                                  SecurityService securityService) {
        this.availabilityService = availabilityService;
        this.modelMapperService = modelMapperService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "') or @securityServiceImpl.isSameUsernameThanAuthenticatedUser(#availabilitySearch.userUsername)")
    @GetMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<List<AvailabilityDto.AvailabilityResponse>> searchForAvailabilities(@Valid AvailabilitySearch availabilitySearch) {
        List<Availability> availabilities = availabilityService.searchAll(availabilitySearch);
        if (availabilities.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_AVAILABILITY_FOUND);
        }
        return new ResponseEntity<>(availabilities.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "') or @availabilityController.isAvailabilityOwner(#id)")
    @GetMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<AvailabilityDto.AvailabilityResponse> getAvailabilityById(@PathVariable Long id) {
        Optional<Availability> availabilityOptional = availabilityService.findById(id);
        Availability availability = availabilityOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "') or @securityServiceImpl.isSameUsernameThanAuthenticatedUser(#availabilityCreateDto.userInfoUsername)")
    @PostMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<AvailabilityDto.AvailabilityResponse> createNewAvailability(@RequestBody @Valid AvailabilityDto.AvailabilityCreate availabilityCreateDto) {
        Availability availability = availabilityService.create(availabilityCreateDto);
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "') or @availabilityController.isAvailabilityOwner(#id)")
    @PutMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<AvailabilityDto.AvailabilityResponse> updateAvailability(@PathVariable Long id, @RequestBody @Valid AvailabilityDto.AvailabilityUpdate updateDto) {
        Availability availability = availabilityService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(availability), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "') or @availabilityController.isAvailabilityOwner(#id)")
    @DeleteMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteAvailability(@PathVariable Long id) {
        availabilityService.delete(availabilityService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id))));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private AvailabilityDto.AvailabilityResponse convertToResponseDto(Availability availability) {
        return modelMapperService.map(availability, AvailabilityDto.AvailabilityResponse.class);
    }

    public boolean isAvailabilityOwner(Long id) {
        Optional<Availability> availability = availabilityService.findById(id);
        return availability.filter(value -> securityService.isSameUsernameThanAuthenticatedUser(value.getUserInfo().getUsername())).isPresent();
    }
}
