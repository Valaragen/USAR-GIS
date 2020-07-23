package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface NotificationService extends CRUDService<Notification, Long> {
    Notification create(NotificationDto.NotificationPostRequest createDto);

    Notification update(Long id, NotificationDto.NotificationPostRequest updateDto);
}
