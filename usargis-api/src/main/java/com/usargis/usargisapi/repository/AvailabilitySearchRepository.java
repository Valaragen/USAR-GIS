package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilitySearchRepository {
    List<Availability> searchAll(AvailabilitySearch availabilitySearch);
}
