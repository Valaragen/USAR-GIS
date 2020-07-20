package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return availabilityRepository.findById(id);
    }

    @Override
    public Availability save(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public void delete(Availability availability) {
        availabilityRepository.delete(availability);
    }

    @Override
    public List<Availability> searchAll(AvailabilitySearch availabilitySearch) {
        return availabilityRepository.searchAll(availabilitySearch);
    }

    @Override
    public Availability update(Long id, Availability availabilityDetails) throws InvocationTargetException, IllegalAccessException {
        Availability availability = findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id)));
        availabilityDetails.setId(null);

        return save(availability);
    }
}
