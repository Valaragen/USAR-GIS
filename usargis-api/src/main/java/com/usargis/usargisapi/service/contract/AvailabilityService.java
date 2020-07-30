package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.common.CRUDService;

import java.util.List;

public interface AvailabilityService extends CRUDService<Availability, Long> {
    List<Availability> searchAll(AvailabilitySearch availabilitySearch);

    Availability create(AvailabilityDto.AvailabilityCreate createDto);

    Availability update(Long id, AvailabilityDto.AvailabilityUpdate updateDto);

    boolean isAvailabilityOwner(Long id);
}
