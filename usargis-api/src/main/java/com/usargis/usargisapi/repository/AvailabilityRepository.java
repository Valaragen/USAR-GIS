package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>, AvailabilitySearchRepository {
}
