package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
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
}
