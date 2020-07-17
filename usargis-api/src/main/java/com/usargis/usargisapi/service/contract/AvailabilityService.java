package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.common.CRUDService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface AvailabilityService extends CRUDService<Availability, Long> {
    List<Availability> searchAll(AvailabilitySearch availabilitySearch);

    Availability update(Long id, Availability availabilityDetails) throws InvocationTargetException, IllegalAccessException;
}
