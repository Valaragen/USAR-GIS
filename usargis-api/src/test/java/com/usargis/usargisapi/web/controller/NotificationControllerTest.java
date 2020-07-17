package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Notification;
import com.usargis.usargisapi.service.contract.NotificationService;
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

class NotificationControllerTest {

    private NotificationController objectToTest;

    private NotificationService notificationService = Mockito.mock(NotificationService.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationController(notificationService);
    }

    @Nested
    class findAllNotificationsTest {
        @Test
        void findAllNotifications_shouldCallServiceLayer() {
            Mockito.when(notificationService.findAll()).thenReturn(Collections.singletonList(new Notification()));

            objectToTest.findAllNotifications();

            Mockito.verify(notificationService).findAll();
        }

        @Test
        void findAllNotifications_noNotificationFound_throwNotFoundException() {
            Mockito.when(notificationService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllNotifications())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_NOTIFICATIONS_FOUND);
        }

        @Test
        void findAllNotifications_notificationFound_returnResponseEntityWithNotificationList() {
            List<Notification> returnedNotificationList = Collections.singletonList(new Notification());
            Mockito.when(notificationService.findAll()).thenReturn(returnedNotificationList);

            ResponseEntity result = objectToTest.findAllNotifications();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedNotificationList);
        }
    }

    @Nested
    class getNotificationByIdTest {
        private final Long notificationIdToFind = 1L;
        private final Notification notificationFound = new Notification();

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
        void getNotificationById_notificationFound_returnResponseEntityWithNotificationAndStatusOk() {
            Mockito.when(notificationService.findById(notificationIdToFind)).thenReturn(Optional.of(notificationFound));

            ResponseEntity result = objectToTest.getNotificationById(notificationIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationFound);
        }
    }

    @Nested
    class createNewNotificationTest {
        private final Notification notificationToSave = new Notification();
        private final Notification savedNotification = new Notification();

        @Test
        void createNewNotification_shouldCallServiceLayer() {
            Mockito.when(notificationService.save(notificationToSave)).thenReturn(savedNotification);

            objectToTest.createNewNotification(notificationToSave);

            Mockito.verify(notificationService).save(notificationToSave);
        }

        @Test
        void createNewNotification_notificationCreated_returnResponseEntityWithNotificationAndStatusCreated() {
            Mockito.when(notificationService.save(notificationToSave)).thenReturn(savedNotification);

            ResponseEntity result = objectToTest.createNewNotification(notificationToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedNotification);
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
                    .isEqualTo(null);
        }
    }

}
