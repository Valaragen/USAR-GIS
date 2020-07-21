package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.embeddable.NotificationMessageId;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface NotificationMessageService extends CRUDService<NotificationMessage, NotificationMessageId> {
    NotificationMessage create(NotificationMessageDto.PostRequest createDto);

    NotificationMessage update(Long id, NotificationMessageDto.PostRequest updateDto);
}
