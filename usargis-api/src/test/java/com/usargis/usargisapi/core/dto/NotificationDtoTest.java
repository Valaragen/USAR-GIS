package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.impl.ModelMapperServiceImpl;
import com.usargis.usargisapi.util.objectMother.dto.NotificationDtoMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class NotificationDtoTest {

    private ModelMapper modelMapper = new ModelMapper();
    private ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);

    @Nested
    class NotificationPostRequestDto {
        @Test
        void notificationPostRequestDto_mapDtoToEntity_shouldMapCorrectly() {
            NotificationDto.PostRequest notificationPostRequestDto = NotificationDtoMother.postRequestSample().build();

            Notification notification = modelMapperService.map(notificationPostRequestDto, Notification.class);

            Assertions.assertThat(notification.getId()).isNull();
            Assertions.assertThat(notification.getMission()).isNull();
            Assertions.assertThat(notification.getEvent()).isNull();
            Assertions.assertThat(notification.getNotificationMessages()).isEmpty();
            Assertions.assertThat(notification.getSendingDate()).isEqualTo(notificationPostRequestDto.getSendingDate());
        }
    }

    @Nested
    class NotificationResponseDto {
        @Test
        void notificationResponseDto_mapToEntity_shouldMapCorrectly() {
            Notification notification = NotificationMother.sampleSent().build();
            notification.setId(1L);
            notification.getAuthor().setId("test");

            NotificationDto.Response notificationResponseDto = modelMapperService.map(notification, NotificationDto.Response.class);

            Assertions.assertThat(notificationResponseDto.getId()).isEqualTo(notification.getId());
            Assertions.assertThat(notificationResponseDto.getStatus()).isEqualTo(notification.getStatus());
            Assertions.assertThat(notificationResponseDto.getSendingDate()).isEqualTo(notification.getSendingDate());
            Assertions.assertThat(notificationResponseDto.getAuthorUsername()).isEqualTo(notification.getAuthor().getUsername());
        }
    }
}
