package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.EventDtoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.*;

class EventControllerTest {

    private EventController objectToTest;

    private EventService eventService = Mockito.mock(EventService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new EventController(eventService, modelMapperService);
    }

    @Nested
    class findAllEventsTest {
        private final List<Event> eventsFound = Arrays.asList(new Event(), new Event());

        @Test
        void findAllEvents_shouldCallServiceLayer() {
            Mockito.when(eventService.findAll()).thenReturn(eventsFound);

            objectToTest.findAllEvents();

            Mockito.verify(eventService).findAll();
        }

        @Test
        void findAllEvents_noEventFound_throwNotFoundException() {
            Mockito.when(eventService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllEvents())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_EVENT_FOUND);
        }

        @Test
        void findAllEvents_shouldConvertEventsToListOfResponseDto() {
            Mockito.when(eventService.findAll()).thenReturn(eventsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Event.class), Mockito.any())).thenReturn(new EventDto.EventResponse());

            objectToTest.findAllEvents();

            Mockito.verify(modelMapperService, Mockito.times(eventsFound.size())).map(Mockito.any(Event.class), Mockito.any());
        }

        @Test
        void findAllEvents_eventFound_returnStatusOkWithListOfEventsResponseDto() {
            Mockito.when(eventService.findAll()).thenReturn(eventsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Event.class), Mockito.any())).thenReturn(new EventDto.EventResponse());

            ResponseEntity<List<EventDto.EventResponse>> result = objectToTest.findAllEvents();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(eventsFound.size());
        }
    }

    @Nested
    class getEventByIdTest {
        private final Long eventIdToFind = 1L;
        private final Event eventFound = new Event();
        private final EventDto.EventResponse eventResponseDto = new EventDto.EventResponse();

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
        void getEventById_shouldConvertEventToResponseDto() {
            Mockito.when(eventService.findById(eventIdToFind)).thenReturn(Optional.of(eventFound));
            Mockito.when(modelMapperService.map(eventFound, EventDto.EventResponse.class)).thenReturn(eventResponseDto);

            objectToTest.getEventById(eventIdToFind);

            Mockito.verify(modelMapperService).map(eventFound, EventDto.EventResponse.class);
        }

        @Test
        void getEventById_eventFound_returnStatusOkAndEventResponseDto() {
            Mockito.when(eventService.findById(eventIdToFind)).thenReturn(Optional.of(eventFound));
            Mockito.when(modelMapperService.map(eventFound, EventDto.EventResponse.class)).thenReturn(eventResponseDto);

            ResponseEntity<EventDto.EventResponse> result = objectToTest.getEventById(eventIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(eventResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(EventDto.EventResponse.class);
        }
    }

    @Nested
    class createNewEventTest {
        private final EventDto.EventPostRequest eventToSave = EventDtoMother.postRequestSample().build();
        private final Event newEvent = new Event();
        private final EventDto.EventResponse eventResponseDto = new EventDto.EventResponse();

        @Test
        void createNewEvent_shouldCallServiceLayer() {
            Mockito.when(eventService.create(eventToSave)).thenReturn(newEvent);

            objectToTest.createNewEvent(eventToSave);

            Mockito.verify(eventService).create(eventToSave);
        }

        @Test
        void createNewEvent_shouldConvertEventToResponseDto() {
            Mockito.when(eventService.create(eventToSave)).thenReturn(newEvent);
            Mockito.when(modelMapperService.map(newEvent, EventDto.EventResponse.class)).thenReturn(eventResponseDto);

            objectToTest.createNewEvent(eventToSave);

            Mockito.verify(modelMapperService).map(newEvent, EventDto.EventResponse.class);
        }

        @Test
        void createNewEvent_eventCreated_returnStatusCreatedAndEventResponseDto() {
            Mockito.when(eventService.create(eventToSave)).thenReturn(newEvent);
            Mockito.when(modelMapperService.map(newEvent, EventDto.EventResponse.class)).thenReturn(eventResponseDto);

            ResponseEntity<EventDto.EventResponse> result = objectToTest.createNewEvent(eventToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(eventResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(EventDto.EventResponse.class);
        }
    }

    @Nested
    class updateEventTest {
        private final Long eventId = 1L;
        private final EventDto.EventPostRequest eventToUpdate = EventDtoMother.postRequestSample().build();
        private final Event updateEvent = new Event();
        private final EventDto.EventResponse eventResponseDto = new EventDto.EventResponse();

        @Test
        void updateEventTest_shouldCallServiceLayer() {
            Mockito.when(eventService.update(eventId, eventToUpdate)).thenReturn(updateEvent);

            objectToTest.updateEvent(eventId, eventToUpdate);

            Mockito.verify(eventService).update(eventId, eventToUpdate);
        }

        @Test
        void updateEventTest_shouldConvertEventToResponseDto() {
            Mockito.when(eventService.update(eventId, eventToUpdate))
                    .thenReturn(updateEvent);
            Mockito.when(modelMapperService.map(updateEvent, EventDto.EventResponse.class))
                    .thenReturn(eventResponseDto);

            objectToTest.updateEvent(eventId, eventToUpdate);

            Mockito.verify(modelMapperService).map(updateEvent, EventDto.EventResponse.class);
        }

        @Test
        void updateEventTest_eventCreated_returnStatusOkAndEventResponseDto() {
            Mockito.when(eventService.update(eventId, eventToUpdate))
                    .thenReturn(updateEvent);
            Mockito.when(modelMapperService.map(updateEvent, EventDto.EventResponse.class))
                    .thenReturn(eventResponseDto);

            ResponseEntity<EventDto.EventResponse> result =
                    objectToTest.updateEvent(eventId, eventToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(eventResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(EventDto.EventResponse.class);
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
                    .isNull();
        }
    }
}
