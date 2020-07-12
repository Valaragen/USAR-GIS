package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.search.AvailabilitySearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilitySearchRepository {
    List<Availability> searchAll(AvailabilitySearch availabilitySearch);
}
