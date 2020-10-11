package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.repository.NotificationMessageRepository;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import com.usargis.usargisapi.service.contract.NotificationService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.NotificationMessageDtoMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMessageMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMother;
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

class NotificationMessageServiceImplTest {

    private NotificationMessageService objectToTest;

    private NotificationMessageRepository notificationMessageRepository = Mockito.mock(NotificationMessageRepository.class);
    private NotificationService notificationService = Mockito.mock(NotificationService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationMessageServiceImpl(notificationMessageRepository, notificationService, modelMapperService);
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
        Long notificationMessageIdToFind = 1L;
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

    @Nested
    class createTest {
        private NotificationMessageDto.NotificationMessagePostRequest notificationMessageCreateDto = NotificationMessageDtoMother.postRequestSample().build();
        private Notification notificationToLink = NotificationMother.sampleSent().build();
        private NotificationMessage savedNotificationMessage = NotificationMessageMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(notificationService.findById(notificationMessageCreateDto.getNotificationId())).thenReturn(Optional.of(notificationToLink));
            Mockito.when(notificationMessageRepository.save(Mockito.any(NotificationMessage.class))).thenReturn(savedNotificationMessage);
        }

        @Test
        void create_noNotificationForGivenId_throwNotFoundException() {
            Mockito.when(notificationService.findById(notificationMessageCreateDto.getNotificationId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.create(notificationMessageCreateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, notificationMessageCreateDto.getNotificationId()));
        }

        @Test
        void create_shouldMapDtoInNotificationMessage() {
            objectToTest.create(notificationMessageCreateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(NotificationMessageDto.class), Mockito.any(NotificationMessage.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(notificationMessageCreateDto);

            Mockito.verify(notificationMessageRepository).save(Mockito.any(NotificationMessage.class));
        }

        @Test
        void create_shouldReturnSavedNotificationMessage() {
            NotificationMessage result = objectToTest.create(notificationMessageCreateDto);

            Assertions.assertThat(result).isEqualTo(savedNotificationMessage);
        }

        @Test
        void create_returnedNotificationMessageShouldContainLinkedEntities() {
            Mockito.when(notificationMessageRepository.save(Mockito.any(NotificationMessage.class))).then(AdditionalAnswers.returnsFirstArg());

            NotificationMessage result = objectToTest.create(notificationMessageCreateDto);

            Assertions.assertThat(result.getNotification()).isEqualTo(notificationToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private NotificationMessage notificationMessageToUpdate = NotificationMessageMother.sample().build();
        private NotificationMessageDto.NotificationMessagePostRequest notificationMessageUpdateDto = NotificationMessageDtoMother.postRequestSample().build();
        private NotificationMessage savedNotificationMessage = NotificationMessageMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(notificationMessageRepository.findById(givenId)).thenReturn(Optional.ofNullable(notificationMessageToUpdate));
            Mockito.when(notificationMessageRepository.save(Mockito.any(NotificationMessage.class))).thenReturn(savedNotificationMessage);
        }

        @Test
        void update_noNotificationMessageForGivenId_throwNotFoundException() {
            Mockito.when(notificationMessageRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.update(givenId, notificationMessageUpdateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInNotificationMessage() {
            objectToTest.update(givenId, notificationMessageUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(NotificationMessageDto.class), Mockito.any(NotificationMessage.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, notificationMessageUpdateDto);

            Mockito.verify(notificationMessageRepository).save(Mockito.any(NotificationMessage.class));
        }

        @Test
        void update_shouldReturnSavedNotificationMessage() {
            NotificationMessage result = objectToTest.update(givenId, notificationMessageUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedNotificationMessage);
        }
    }
}
