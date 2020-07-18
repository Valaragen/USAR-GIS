package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.embeddable.NotificationMessageId;
import com.usargis.usargisapi.repository.NotificationMessageRepository;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class NotificationMessageServiceImplTest {

    private NotificationMessageService objectToTest;

    private NotificationMessageRepository notificationMessageRepository = Mockito.mock(NotificationMessageRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationMessageServiceImpl(notificationMessageRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<NotificationMessage> returnedNotificationMessageList = Collections.singletonList(new NotificationMessage());
        Mockito.when(notificationMessageRepository.findAll()).thenReturn(returnedNotificationMessageList);

        List<NotificationMessage> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedNotificationMessageList);
        Mockito.verify(notificationMessageRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        NotificationMessage notificationMessageFound = new NotificationMessage();
        Optional<NotificationMessage> expectedResult = Optional.of(notificationMessageFound);
        NotificationMessageId notificationMessageIdToFind = new NotificationMessageId();
        Mockito.when(notificationMessageRepository.findById(notificationMessageIdToFind)).thenReturn(expectedResult);

        Optional<NotificationMessage> result = objectToTest.findById(notificationMessageIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(notificationMessageRepository).findById(notificationMessageIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnNotificationMessage() {
        NotificationMessage notificationMessageToSave = new NotificationMessage();
        Mockito.when(notificationMessageRepository.save(notificationMessageToSave)).thenReturn(notificationMessageToSave);

        NotificationMessage result = objectToTest.save(notificationMessageToSave);

        Assertions.assertThat(result).isEqualTo(notificationMessageToSave);
        Mockito.verify(notificationMessageRepository).save(notificationMessageToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        NotificationMessage notificationMessageToDelete = new NotificationMessage();
        Mockito.doNothing().when(notificationMessageRepository).delete(notificationMessageToDelete);

        objectToTest.delete(notificationMessageToDelete);

        Mockito.verify(notificationMessageRepository).delete(notificationMessageToDelete);
    }

}
