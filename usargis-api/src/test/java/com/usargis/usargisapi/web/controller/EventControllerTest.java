package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Event;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.impl.EventServiceImpl;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EventControllerTest {

    private EventController objectToTest;

    private EventService eventService = Mockito.mock(EventService.class);

    @BeforeEach
    void setup() {
        objectToTest = new EventController(eventService);
    }

    @Nested
    class findAllEventsTest {
        @Test
        void findAllEvents_shouldCallServiceLayer() {
            Mockito.when(eventService.findAll()).thenReturn(Collections.singletonList(new Event()));

            objectToTest.findAllEvents();

            Mockito.verify(eventService).findAll();
        }

        @Test
        void findAllEvents_noEventFound_throwNotFoundException() {
            Mockito.when(eventService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllEvents())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_EVENTS_FOUND);
        }

        @Test
        void findAllEvents_eventFound_returnResponseEntityWithEventList() {
            List<Event> returnedEventList = Collections.singletonList(new Event());
            Mockito.when(eventService.findAll()).thenReturn(returnedEventList);

            ResponseEntity result = objectToTest.findAllEvents();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedEventList);
        }
    }

    @Nested
    class getEventByIdTest {
        private final Long eventIdToFind = 1L;
        private final Event eventFound = new Event();

        @Test
        void getEventById_shouldCallServiceLayer() {
            Mockito.when(eventService.findById(eventIdToFind)).thenReturn(Optional.of(eventFound));

            objectToTest.getEventById(eventIdToFind);

            Mockito.verify(eventService).findById(eventIdToFind);
        }

        @Test
        void getEventById_noEventFound_throwNotFoundException() {
            Mockito.when(eventService.findById(eventIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getEventById(eventIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, eventIdToFind));
        }

        @Test
        void getEventById_eventFound_returnResponseEntityWithEventAndStatusOk() {
            Mockito.when(eventService.findById(eventIdToFind)).thenReturn(Optional.of(eventFound));

            ResponseEntity result = objectToTest.getEventById(eventIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(eventFound);
        }
    }

    @Nested
    class createNewEventTest {
        private final Event eventToSave = new Event();
        private final Event savedEvent = new Event();

        @Test
        void createNewEvent_shouldCallServiceLayer() {
            Mockito.when(eventService.save(eventToSave)).thenReturn(savedEvent);

            objectToTest.createNewEvent(eventToSave);

            Mockito.verify(eventService).save(eventToSave);
        }

        @Test
        void createNewEvent_eventCreated_returnResponseEntityWithEventAndStatusCreated() {
            Mockito.when(eventService.save(eventToSave)).thenReturn(savedEvent);

            ResponseEntity result = objectToTest.createNewEvent(eventToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedEvent);
        }
    }

    @Nested
    class deleteEventTest {
        private final Long eventToDeleteId = 1L;
        private final Event foundEventToDelete = new Event();

        @Test
        void deleteEvent_shouldCallServiceLayer() {
            Mockito.when(eventService.findById(eventToDeleteId)).thenReturn(Optional.of(foundEventToDelete));
            Mockito.doNothing().when(eventService).delete(foundEventToDelete);

            objectToTest.deleteEvent(eventToDeleteId);

            Mockito.verify(eventService).findById(eventToDeleteId);
            Mockito.verify(eventService).delete(foundEventToDelete);
        }

        @Test
        void deleteEvent_eventDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(eventService.findById(eventToDeleteId)).thenReturn(Optional.of(foundEventToDelete));
            Mockito.doNothing().when(eventService).delete(foundEventToDelete);

            ResponseEntity result = objectToTest.deleteEvent(eventToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(null);
        }
    }

}
