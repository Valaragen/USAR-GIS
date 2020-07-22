package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.*;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.repository.NotificationRepository;
import com.usargis.usargisapi.service.contract.*;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.NotificationDtoMother;
import com.usargis.usargisapi.util.objectMother.model.EventMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMother;
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

class NotificationServiceImplTest {

    private NotificationService objectToTest;

    private NotificationRepository notificationRepository = Mockito.mock(NotificationRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private EventService eventService = Mockito.mock(EventService.class);
    private MissionService missionService = Mockito.mock(MissionService.class);
    private SecurityService securityService = Mockito.mock(SecurityService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationServiceImpl(notificationRepository, userInfoService,
                eventService, missionService, securityService, modelMapperService);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Notification> returnedNotificationList = Collections.singletonList(new Notification());
        Mockito.when(notificationRepository.findAll()).thenReturn(returnedNotificationList);

        List<Notification> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedNotificationList);
        Mockito.verify(notificationRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Notification notificationFound = new Notification();
        Optional<Notification> expectedResult = Optional.of(notificationFound);
        Long notificationIdToFind = 1L;
        Mockito.when(notificationRepository.findById(notificationIdToFind)).thenReturn(expectedResult);

        Optional<Notification> result = objectToTest.findById(notificationIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(notificationRepository).findById(notificationIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnNotification() {
        Notification notificationToSave = new Notification();
        Mockito.when(notificationRepository.save(notificationToSave)).thenReturn(notificationToSave);

        Notification result = objectToTest.save(notificationToSave);

        Assertions.assertThat(result).isEqualTo(notificationToSave);
        Mockito.verify(notificationRepository).save(notificationToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Notification notificationToDelete = new Notification();
        Mockito.doNothing().when(notificationRepository).delete(notificationToDelete);

        objectToTest.delete(notificationToDelete);

        Mockito.verify(notificationRepository).delete(notificationToDelete);
    }

    @Nested
    class createTest {
        private NotificationDto.PostRequest notificationPostRequestDto = NotificationDtoMother.postRequestSample().build();
        private NotificationDto.PostRequest nullIdsNotificationPostRequestDto = NotificationDto.PostRequest.builder().build();
        private UserInfo authorToLink = UserInfoMother.sampleAuthor().build();
        private Event eventToLink = EventMother.sampleFinished().build();
        private Mission missionToLink = MissionMother.sampleFinished().build();
        private String userNameFromToken = authorToLink.getUsername();
        private Notification savedNotification = NotificationMother.sampleSent().build();

        @BeforeEach
        void setup() {
            Mockito.when(securityService.getUsernameFromToken()).thenReturn(userNameFromToken);
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.of(authorToLink));
            Mockito.when(missionService.findById(notificationPostRequestDto.getMissionId())).thenReturn(Optional.of(missionToLink));
            Mockito.when(eventService.findById(notificationPostRequestDto.getEventId())).thenReturn(Optional.of(eventToLink));
            Mockito.when(notificationRepository.save(Mockito.any(Notification.class))).thenReturn(savedNotification);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(notificationPostRequestDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, userNameFromToken));
        }

        @Test
        void create_dtoMissionIdIsNotNullAndNotFound_throwNotFoundException() {
            Mockito.when(missionService.findById(notificationPostRequestDto.getMissionId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(notificationPostRequestDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, notificationPostRequestDto.getMissionId()));
        }

        @Test
        void create_dtoMissionIdIsNull_shouldSkipDbSearch() {
            objectToTest.create(nullIdsNotificationPostRequestDto);

            Mockito.verify(missionService, Mockito.never()).findById(notificationPostRequestDto.getMissionId());
        }

        @Test
        void create_dtoEventIdIsNotNullAndNotFound_throwNotFoundException() {
            Mockito.when(eventService.findById(notificationPostRequestDto.getEventId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(notificationPostRequestDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, notificationPostRequestDto.getEventId()));
        }

        @Test
        void create_dtoEventIdIsNull_shouldSkipDbSearch() {
            objectToTest.create(nullIdsNotificationPostRequestDto);

            Mockito.verify(eventService, Mockito.never()).findById(notificationPostRequestDto.getEventId());
        }

        @Test
        void create_shouldMapDtoInNotification() {
            objectToTest.create(notificationPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(NotificationDto.class), Mockito.any(Notification.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(notificationPostRequestDto);

            Mockito.verify(notificationRepository).save(Mockito.any(Notification.class));
        }

        @Test
        void create_shouldReturnSavedNotification() {
            Notification result = objectToTest.create(notificationPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedNotification);
        }

        @Test
        void create_notNullIdsInDto_returnNotificationWithLinkedEntities() {
            Mockito.when(notificationRepository.save(Mockito.any(Notification.class))).then(AdditionalAnswers.returnsFirstArg());

            Notification result = objectToTest.create(notificationPostRequestDto);

            Assertions.assertThat(result.getMission()).isEqualTo(missionToLink);
            Assertions.assertThat(result.getEvent()).isEqualTo(eventToLink);
            Assertions.assertThat(result.getAuthor()).isEqualTo(authorToLink);
        }

        @Test
        void create_nullIdsInDto_returnNotificationWithoutLinkOnNullIdEntities() {
            Mockito.when(notificationRepository.save(Mockito.any(Notification.class))).then(AdditionalAnswers.returnsFirstArg());

            Notification result = objectToTest.create(nullIdsNotificationPostRequestDto);

            Assertions.assertThat(result.getMission()).isNull();
            Assertions.assertThat(result.getEvent()).isNull();
            Assertions.assertThat(result.getAuthor()).isEqualTo(authorToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Notification notificationToUpdate = NotificationMother.sampleSent().build();
        private NotificationDto.PostRequest notificationUpdateDto = NotificationDtoMother.postRequestSample().build();
        private Notification savedNotification = NotificationMother.sampleSent().build();

        @BeforeEach
        void setup() {
            Mockito.when(notificationRepository.findById(givenId)).thenReturn(Optional.ofNullable(notificationToUpdate));
            Mockito.when(notificationRepository.save(Mockito.any(Notification.class))).thenReturn(savedNotification);
        }

        @Test
        void update_noNotificationForGivenId_throwNotFoundException() {
            Mockito.when(notificationRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.update(givenId, notificationUpdateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInNotification() {
            objectToTest.update(givenId, notificationUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(NotificationDto.class), Mockito.any(Notification.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, notificationUpdateDto);

            Mockito.verify(notificationRepository).save(Mockito.any(Notification.class));
        }

        @Test
        void update_shouldReturnSavedNotification() {
            Notification result = objectToTest.update(givenId, notificationUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedNotification);
        }
    }
}
