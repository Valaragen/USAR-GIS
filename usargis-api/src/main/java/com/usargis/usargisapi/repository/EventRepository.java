package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
