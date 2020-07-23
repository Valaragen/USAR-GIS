package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.NotificationMessageContentType;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.NotificationMessageDtoMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMessageMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class NotificationMessageDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class NotificationMessagePostRequestDto {
        @Test
        void notificationMessagePostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            NotificationMessageDto.NotificationMessagePostRequest notificationMessagePostRequestDto = NotificationMessageDtoMother.postRequestSample().build();

            NotificationMessage notificationMessage = modelMapperService.map(notificationMessagePostRequestDto, NotificationMessage.class);

            Assertions.assertThat(notificationMessage.getId()).isNull();
            Assertions.assertThat(notificationMessage.getContentType()).isEqualTo(notificationMessagePostRequestDto.getContentType());
            Assertions.assertThat(notificationMessage.getSendingModes()).isEqualTo(notificationMessagePostRequestDto.getSendingModes());
            Assertions.assertThat(notificationMessage.getSubject()).isEqualTo(notificationMessagePostRequestDto.getSubject());
            Assertions.assertThat(notificationMessage.getContent()).isEqualTo(notificationMessagePostRequestDto.getContent());
        }
    }

    @Nested
    class NotificationMessageResponseDto {
        @Test
        void notificationMessageResponseDto_mapEntityToDto_shouldMapCorrectly() {
            NotificationMessage notificationMessage = NotificationMessageMother.sample().build();
            notificationMessage.getNotification().setId(1L);
            notificationMessage.setContentType(NotificationMessageContentType.TEXT);

            NotificationMessageDto.NotificationMessageResponse notificationMessageResponseDto = modelMapperService.map(notificationMessage, NotificationMessageDto.NotificationMessageResponse.class);

            Assertions.assertThat(notificationMessageResponseDto.getNotificationId()).isEqualTo(notificationMessage.getNotification().getId());
            Assertions.assertThat(notificationMessageResponseDto.getContentType()).isEqualTo(notificationMessage.getContentType());
            Assertions.assertThat(notificationMessageResponseDto.getSendingModes()).isEqualTo(notificationMessage.getSendingModes());
            Assertions.assertThat(notificationMessageResponseDto.getContent()).isEqualTo(notificationMessage.getContent());
            Assertions.assertThat(notificationMessageResponseDto.getSubject()).isEqualTo(notificationMessage.getSubject());
        }
    }
}
