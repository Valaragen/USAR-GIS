package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface NotificationMessageService extends CRUDService<NotificationMessage, Long> {
    NotificationMessage create(NotificationMessageDto.NotificationMessagePostRequest createDto);

    NotificationMessage update(Long id, NotificationMessageDto.NotificationMessagePostRequest updateDto);
}
