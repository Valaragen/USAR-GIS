package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.service.contract.NotificationService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.NotificationDtoMother;
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

class NotificationControllerTest {
    private NotificationController objectToTest;

    private NotificationService notificationService = Mockito.mock(NotificationService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationController(notificationService, modelMapperService);
    }

    @Nested
    class findAllNotificationsTest {
        private final List<Notification> notificationsFound = Arrays.asList(new Notification(), new Notification());

        @Test
        void findAllNotifications_shouldCallServiceLayer() {
            Mockito.when(notificationService.findAll()).thenReturn(notificationsFound);

            objectToTest.findAllNotifications();

            Mockito.verify(notificationService).findAll();
        }

        @Test
        void findAllNotifications_noNotificationFound_throwNotFoundException() {
            Mockito.when(notificationService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllNotifications())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_NOTIFICATIONS_FOUND);
        }

        @Test
        void findAllNotifications_shouldConvertNotificationsToListOfResponseDto() {
            Mockito.when(notificationService.findAll()).thenReturn(notificationsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Notification.class), Mockito.any())).thenReturn(new NotificationDto.Response());

            objectToTest.findAllNotifications();

            Mockito.verify(modelMapperService, Mockito.times(notificationsFound.size())).map(Mockito.any(Notification.class), Mockito.any());
        }

        @Test
        void findAllNotifications_notificationFound_returnStatusOkWithListOfNotificationsResponseDto() {
            Mockito.when(notificationService.findAll()).thenReturn(notificationsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Notification.class), Mockito.any())).thenReturn(new NotificationDto.Response());

            ResponseEntity<List<NotificationDto.Response>> result = objectToTest.findAllNotifications();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(notificationsFound.size());
        }
    }

    @Nested
    class getNotificationByIdTest {
        private final Long notificationIdToFind = 1L;
        private final Notification notificationFound = new Notification();
        private final NotificationDto.Response notificationResponseDto = new NotificationDto.Response();

        @Test
        void getNotificationById_shouldCallServiceLayer() {
            Mockito.when(notificationService.findById(notificationIdToFind)).thenReturn(Optional.of(notificationFound));

            objectToTest.getNotificationById(notificationIdToFind);

            Mockito.verify(notificationService).findById(notificationIdToFind);
        }

        @Test
        void getNotificationById_noNotificationFound_throwNotFoundException() {
            Mockito.when(notificationService.findById(notificationIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getNotificationById(notificationIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, notificationIdToFind));
        }

        @Test
        void getNotificationById_shouldConvertNotificationToResponseDto() {
            Mockito.when(notificationService.findById(notificationIdToFind)).thenReturn(Optional.of(notificationFound));
            Mockito.when(modelMapperService.map(notificationFound, NotificationDto.Response.class)).thenReturn(notificationResponseDto);

            objectToTest.getNotificationById(notificationIdToFind);

            Mockito.verify(modelMapperService).map(notificationFound, NotificationDto.Response.class);
        }

        @Test
        void getNotificationById_notificationFound_returnStatusOkAndNotificationResponseDto() {
            Mockito.when(notificationService.findById(notificationIdToFind)).thenReturn(Optional.of(notificationFound));
            Mockito.when(modelMapperService.map(notificationFound, NotificationDto.Response.class)).thenReturn(notificationResponseDto);

            ResponseEntity<NotificationDto.Response> result = objectToTest.getNotificationById(notificationIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationDto.Response.class);
        }
    }

    @Nested
    class createNewNotificationTest {
        private final NotificationDto.PostRequest notificationToSave = NotificationDtoMother.postRequestSample().build();
        private final Notification newNotification = new Notification();
        private final NotificationDto.Response notificationResponseDto = new NotificationDto.Response();

        @Test
        void createNewNotification_shouldCallServiceLayer() {
            Mockito.when(notificationService.create(notificationToSave)).thenReturn(newNotification);

            objectToTest.createNewNotification(notificationToSave);

            Mockito.verify(notificationService).create(notificationToSave);
        }

        @Test
        void createNewNotification_shouldConvertNotificationToResponseDto() {
            Mockito.when(notificationService.create(notificationToSave)).thenReturn(newNotification);
            Mockito.when(modelMapperService.map(newNotification, NotificationDto.Response.class)).thenReturn(notificationResponseDto);

            objectToTest.createNewNotification(notificationToSave);

            Mockito.verify(modelMapperService).map(newNotification, NotificationDto.Response.class);
        }

        @Test
        void createNewNotification_notificationCreated_returnStatusCreatedAndNotificationResponseDto() {
            Mockito.when(notificationService.create(notificationToSave)).thenReturn(newNotification);
            Mockito.when(modelMapperService.map(newNotification, NotificationDto.Response.class)).thenReturn(notificationResponseDto);

            ResponseEntity<NotificationDto.Response> result = objectToTest.createNewNotification(notificationToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationDto.Response.class);
        }
    }

    @Nested
    class updateNotificationTest {
        private final Long notificationId = 1L;
        private final NotificationDto.PostRequest notificationToUpdate = NotificationDtoMother.postRequestSample().build();
        private final Notification updateNotification = new Notification();
        private final NotificationDto.Response notificationResponseDto = new NotificationDto.Response();

        @Test
        void updateNotificationTest_shouldCallServiceLayer() {
            Mockito.when(notificationService.update(notificationId, notificationToUpdate)).thenReturn(updateNotification);

            objectToTest.updateNotification(notificationId, notificationToUpdate);

            Mockito.verify(notificationService).update(notificationId, notificationToUpdate);
        }

        @Test
        void updateNotificationTest_shouldConvertNotificationToResponseDto() {
            Mockito.when(notificationService.update(notificationId, notificationToUpdate))
                    .thenReturn(updateNotification);
            Mockito.when(modelMapperService.map(updateNotification, NotificationDto.Response.class))
                    .thenReturn(notificationResponseDto);

            objectToTest.updateNotification(notificationId, notificationToUpdate);

            Mockito.verify(modelMapperService).map(updateNotification, NotificationDto.Response.class);
        }

        @Test
        void updateNotificationTest_notificationCreated_returnStatusOkAndNotificationResponseDto() {
            Mockito.when(notificationService.update(notificationId, notificationToUpdate))
                    .thenReturn(updateNotification);
            Mockito.when(modelMapperService.map(updateNotification, NotificationDto.Response.class))
                    .thenReturn(notificationResponseDto);

            ResponseEntity<NotificationDto.Response> result =
                    objectToTest.updateNotification(notificationId, notificationToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationDto.Response.class);
        }
    }

    @Nested
    class deleteNotificationTest {
        private final Long notificationToDeleteId = 1L;
        private final Notification foundNotificationToDelete = new Notification();

        @Test
        void deleteNotification_shouldCallServiceLayer() {
            Mockito.when(notificationService.findById(notificationToDeleteId)).thenReturn(Optional.of(foundNotificationToDelete));
            Mockito.doNothing().when(notificationService).delete(foundNotificationToDelete);

            objectToTest.deleteNotification(notificationToDeleteId);

            Mockito.verify(notificationService).findById(notificationToDeleteId);
            Mockito.verify(notificationService).delete(foundNotificationToDelete);
        }

        @Test
        void deleteNotification_notificationDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(notificationService.findById(notificationToDeleteId)).thenReturn(Optional.of(foundNotificationToDelete));
            Mockito.doNothing().when(notificationService).delete(foundNotificationToDelete);

            ResponseEntity result = objectToTest.deleteNotification(notificationToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
