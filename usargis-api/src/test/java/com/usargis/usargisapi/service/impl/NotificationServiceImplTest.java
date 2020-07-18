package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.repository.NotificationRepository;
import com.usargis.usargisapi.service.contract.NotificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class NotificationServiceImplTest {

    private NotificationService objectToTest;

    private NotificationRepository notificationRepository = Mockito.mock(NotificationRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationServiceImpl(notificationRepository);
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

}
