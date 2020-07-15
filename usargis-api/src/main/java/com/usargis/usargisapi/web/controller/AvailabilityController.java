package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@RestController
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<List<Availability>> searchForAvailabilities(AvailabilitySearch availabilitySearch) {
        List<Availability> availabilities = availabilityService.searchAll(availabilitySearch);
        if (availabilities.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_AVAILABILITIES_FOUND);
        }
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }

    @GetMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable Long id) {
        Optional<Availability> availability = availabilityService.findById(id);
        Availability result = availability.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(Constant.AVAILABILITIES_PATH)
    public ResponseEntity<Availability> createNewAvailability(@RequestBody Availability availability) {
        Availability result = availabilityService.save(availability);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Availability> updateAvailability(@PathVariable Long id, @RequestBody Availability updatedFields) throws InvocationTargetException, IllegalAccessException {
        Availability result = availabilityService.update(id, updatedFields);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Availability> deleteAvailability(@PathVariable Long id) {
        availabilityService.delete(availabilityService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}