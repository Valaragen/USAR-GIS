package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.repository.EventRepository;
import com.usargis.usargisapi.service.contract.EventService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EventServiceImplTest {

    private EventService objectToTest;

    private EventRepository eventRepository = Mockito.mock(EventRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new EventServiceImpl(eventRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Event> returnedEventList = Collections.singletonList(new Event());
        Mockito.when(eventRepository.findAll()).thenReturn(returnedEventList);

        List<Event> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedEventList);
        Mockito.verify(eventRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Event eventFound = new Event();
        Optional<Event> expectedResult = Optional.of(eventFound);
        Long eventIdToFind = 1L;
        Mockito.when(eventRepository.findById(eventIdToFind)).thenReturn(expectedResult);

        Optional<Event> result = objectToTest.findById(eventIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(eventRepository).findById(eventIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnEvent() {
        Event eventToSave = new Event();
        Mockito.when(eventRepository.save(eventToSave)).thenReturn(eventToSave);

        Event result = objectToTest.save(eventToSave);

        Assertions.assertThat(result).isEqualTo(eventToSave);
        Mockito.verify(eventRepository).save(eventToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Event eventToDelete = new Event();
        Mockito.doNothing().when(eventRepository).delete(eventToDelete);

        objectToTest.delete(eventToDelete);

        Mockito.verify(eventRepository).delete(eventToDelete);
    }

}
