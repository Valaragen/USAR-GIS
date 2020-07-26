package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.EventRepository;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.EventDtoMother;
import com.usargis.usargisapi.util.objectMother.model.EventMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EventServiceImplTest {

    private EventService objectToTest;

    private EventRepository eventRepository = Mockito.mock(EventRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);
    private SecurityService securityService = Mockito.mock(SecurityService.class);

    @BeforeEach
    void setup() {
        objectToTest = new EventServiceImpl(eventRepository, userInfoService, modelMapperService, securityService);
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

    @Nested
    class createTest {
        private EventDto.EventPostRequest eventPostRequestDto = EventDtoMother.postRequestSample().build();
        private UserInfo authorToLink = UserInfoMother.sampleAuthor().build();
        private String userNameFromToken = authorToLink.getUsername();
        private Event savedEvent = EventMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(securityService.getUsernameFromToken()).thenReturn(userNameFromToken);
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.of(authorToLink));
            Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(savedEvent);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(eventPostRequestDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, userNameFromToken));
        }

        @Test
        void create_shouldMapDtoInEvent() {
            objectToTest.create(eventPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(EventDto.class), Mockito.any(Event.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(eventPostRequestDto);

            Mockito.verify(eventRepository).save(Mockito.any(Event.class));
        }

        @Test
        void create_shouldReturnSavedEvent() {
            Event result = objectToTest.create(eventPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedEvent);
        }

        @Test
        void create_returnedEventShouldContainLinkedEntities() {
            Mockito.when(eventRepository.save(Mockito.any(Event.class))).then(AdditionalAnswers.returnsFirstArg());

            Event result = objectToTest.create(eventPostRequestDto);

            Assertions.assertThat(result.getAuthor()).isEqualTo(authorToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Event eventToUpdate = EventMother.sampleFinished().build();
        private EventDto.EventPostRequest eventUpdateDto = EventDtoMother.postRequestSample().build();
        private Event savedEvent = EventMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(eventRepository.findById(givenId)).thenReturn(Optional.ofNullable(eventToUpdate));
            Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(savedEvent);
        }

        @Test
        void update_noEventForGivenId_throwNotFoundException() {
            Mockito.when(eventRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.update(givenId, eventUpdateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInEvent() {
            objectToTest.update(givenId, eventUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(EventDto.class), Mockito.any(Event.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, eventUpdateDto);

            Mockito.verify(eventRepository).save(Mockito.any(Event.class));
        }

        @Test
        void update_shouldReturnSavedEvent() {
            Event result = objectToTest.update(givenId, eventUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedEvent);
        }
    }
}
