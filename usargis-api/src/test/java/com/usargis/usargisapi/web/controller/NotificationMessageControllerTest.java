package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.NotificationMessageDtoMother;
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

class NotificationMessageControllerTest {

    private NotificationMessageController objectToTest;

    private NotificationMessageService notificationMessageService = Mockito.mock(NotificationMessageService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new NotificationMessageController(notificationMessageService, modelMapperService);
    }

    @Nested
    class findAllNotificationMessagesTest {
        private final List<NotificationMessage> notificationMessagesFound = Arrays.asList(new NotificationMessage(), new NotificationMessage());

        @Test
        void findAllNotificationMessages_shouldCallServiceLayer() {
            Mockito.when(notificationMessageService.findAll()).thenReturn(notificationMessagesFound);

            objectToTest.findAllNotificationMessages();

            Mockito.verify(notificationMessageService).findAll();
        }

        @Test
        void findAllNotificationMessages_noNotificationMessageFound_throwNotFoundException() {
            Mockito.when(notificationMessageService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllNotificationMessages())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND);
        }

        @Test
        void findAllNotificationMessages_shouldConvertNotificationMessagesToListOfResponseDto() {
            Mockito.when(notificationMessageService.findAll()).thenReturn(notificationMessagesFound);
            Mockito.when(modelMapperService.map(Mockito.any(NotificationMessage.class), Mockito.any())).thenReturn(new NotificationMessageDto.Response());

            objectToTest.findAllNotificationMessages();

            Mockito.verify(modelMapperService, Mockito.times(notificationMessagesFound.size())).map(Mockito.any(NotificationMessage.class), Mockito.any());
        }

        @Test
        void findAllNotificationMessages_notificationMessageFound_returnStatusOkWithListOfNotificationMessagesResponseDto() {
            Mockito.when(notificationMessageService.findAll()).thenReturn(notificationMessagesFound);
            Mockito.when(modelMapperService.map(Mockito.any(NotificationMessage.class), Mockito.any())).thenReturn(new NotificationMessageDto.Response());

            ResponseEntity<List<NotificationMessageDto.Response>> result = objectToTest.findAllNotificationMessages();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(notificationMessagesFound.size());
        }
    }

    @Nested
    class getNotificationMessageByIdTest {
        private final Long notificationMessageIdToFind = 1L;
        private final NotificationMessage notificationMessageFound = new NotificationMessage();
        private final NotificationMessageDto.Response notificationMessageResponseDto = new NotificationMessageDto.Response();

        @Test
        void getNotificationMessageById_shouldCallServiceLayer() {
            Mockito.when(notificationMessageService.findById(notificationMessageIdToFind)).thenReturn(Optional.of(notificationMessageFound));

            objectToTest.getNotificationMessageById(notificationMessageIdToFind);

            Mockito.verify(notificationMessageService).findById(notificationMessageIdToFind);
        }

        @Test
        void getNotificationMessageById_noNotificationMessageFound_throwNotFoundException() {
            Mockito.when(notificationMessageService.findById(notificationMessageIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getNotificationMessageById(notificationMessageIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID, notificationMessageIdToFind));
        }

        @Test
        void getNotificationMessageById_shouldConvertNotificationMessageToResponseDto() {
            Mockito.when(notificationMessageService.findById(notificationMessageIdToFind)).thenReturn(Optional.of(notificationMessageFound));
            Mockito.when(modelMapperService.map(notificationMessageFound, NotificationMessageDto.Response.class)).thenReturn(notificationMessageResponseDto);

            objectToTest.getNotificationMessageById(notificationMessageIdToFind);

            Mockito.verify(modelMapperService).map(notificationMessageFound, NotificationMessageDto.Response.class);
        }

        @Test
        void getNotificationMessageById_notificationMessageFound_returnStatusOkAndNotificationMessageResponseDto() {
            Mockito.when(notificationMessageService.findById(notificationMessageIdToFind)).thenReturn(Optional.of(notificationMessageFound));
            Mockito.when(modelMapperService.map(notificationMessageFound, NotificationMessageDto.Response.class)).thenReturn(notificationMessageResponseDto);

            ResponseEntity<NotificationMessageDto.Response> result = objectToTest.getNotificationMessageById(notificationMessageIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationMessageResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationMessageDto.Response.class);
        }
    }

    @Nested
    class createNewNotificationMessageTest {
        private final NotificationMessageDto.PostRequest notificationMessageToSave = NotificationMessageDtoMother.postRequestSample().build();
        private final NotificationMessage newNotificationMessage = new NotificationMessage();
        private final NotificationMessageDto.Response notificationMessageResponseDto = new NotificationMessageDto.Response();

        @Test
        void createNewNotificationMessage_shouldCallServiceLayer() {
            Mockito.when(notificationMessageService.create(notificationMessageToSave)).thenReturn(newNotificationMessage);

            objectToTest.createNewNotificationMessage(notificationMessageToSave);

            Mockito.verify(notificationMessageService).create(notificationMessageToSave);
        }

        @Test
        void createNewNotificationMessage_shouldConvertNotificationMessageToResponseDto() {
            Mockito.when(notificationMessageService.create(notificationMessageToSave)).thenReturn(newNotificationMessage);
            Mockito.when(modelMapperService.map(newNotificationMessage, NotificationMessageDto.Response.class))
                    .thenReturn(notificationMessageResponseDto);

            objectToTest.createNewNotificationMessage(notificationMessageToSave);

            Mockito.verify(modelMapperService).map(newNotificationMessage, NotificationMessageDto.Response.class);
        }

        @Test
        void createNewNotificationMessage_notificationMessageCreated_returnStatusCreatedAndNotificationMessageResponseDto() {
            Mockito.when(notificationMessageService.create(notificationMessageToSave)).thenReturn(newNotificationMessage);
            Mockito.when(modelMapperService.map(newNotificationMessage, NotificationMessageDto.Response.class))
                    .thenReturn(notificationMessageResponseDto);

            ResponseEntity<NotificationMessageDto.Response> result = objectToTest.createNewNotificationMessage(notificationMessageToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationMessageResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationMessageDto.Response.class);
        }
    }

    @Nested
    class updateNotificationMessageTest {
        private final Long notificationMessageId = 1L;
        private final NotificationMessageDto.PostRequest notificationMessageToUpdate = NotificationMessageDtoMother.postRequestSample().build();
        private final NotificationMessage updateNotificationMessage = new NotificationMessage();
        private final NotificationMessageDto.Response notificationMessageResponseDto = new NotificationMessageDto.Response();

        @Test
        void updateNotificationMessageTest_shouldCallServiceLayer() {
            Mockito.when(notificationMessageService.update(notificationMessageId, notificationMessageToUpdate))
                    .thenReturn(updateNotificationMessage);

            objectToTest.updateNotificationMessage(notificationMessageId, notificationMessageToUpdate);

            Mockito.verify(notificationMessageService).update(notificationMessageId, notificationMessageToUpdate);
        }

        @Test
        void updateNotificationMessageTest_shouldConvertNotificationMessageToResponseDto() {
            Mockito.when(notificationMessageService.update(notificationMessageId, notificationMessageToUpdate))
                    .thenReturn(updateNotificationMessage);
            Mockito.when(modelMapperService.map(updateNotificationMessage, NotificationMessageDto.Response.class))
                    .thenReturn(notificationMessageResponseDto);

            objectToTest.updateNotificationMessage(notificationMessageId, notificationMessageToUpdate);

            Mockito.verify(modelMapperService).map(updateNotificationMessage, NotificationMessageDto.Response.class);
        }

        @Test
        void updateNotificationMessageTest_notificationMessageCreated_returnStatusOkAndNotificationMessageResponseDto() {
            Mockito.when(notificationMessageService.update(notificationMessageId, notificationMessageToUpdate))
                    .thenReturn(updateNotificationMessage);
            Mockito.when(modelMapperService.map(updateNotificationMessage, NotificationMessageDto.Response.class))
                    .thenReturn(notificationMessageResponseDto);

            ResponseEntity<NotificationMessageDto.Response> result =
                    objectToTest.updateNotificationMessage(notificationMessageId, notificationMessageToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(notificationMessageResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(NotificationMessageDto.Response.class);
        }
    }

    @Nested
    class deleteNotificationMessageTest {
        private final Long notificationMessageToDeleteId = 1L;
        private final NotificationMessage foundNotificationMessageToDelete = new NotificationMessage();

        @Test
        void deleteNotificationMessage_shouldCallServiceLayer() {
            Mockito.when(notificationMessageService.findById(notificationMessageToDeleteId))
                    .thenReturn(Optional.of(foundNotificationMessageToDelete));
            Mockito.doNothing().when(notificationMessageService).delete(foundNotificationMessageToDelete);

            objectToTest.deleteNotificationMessage(notificationMessageToDeleteId);

            Mockito.verify(notificationMessageService).findById(notificationMessageToDeleteId);
            Mockito.verify(notificationMessageService).delete(foundNotificationMessageToDelete);
        }

        @Test
        void deleteNotificationMessage_notificationMessageDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(notificationMessageService.findById(notificationMessageToDeleteId))
                    .thenReturn(Optional.of(foundNotificationMessageToDelete));
            Mockito.doNothing().when(notificationMessageService).delete(foundNotificationMessageToDelete);

            ResponseEntity result = objectToTest.deleteNotificationMessage(notificationMessageToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
