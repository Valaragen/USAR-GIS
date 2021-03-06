package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface EventService extends CRUDService<Event, Long> {
    Event create(EventDto.EventPostRequest createDto);

    Event update(Long id, EventDto.EventPostRequest updateDto);
}
