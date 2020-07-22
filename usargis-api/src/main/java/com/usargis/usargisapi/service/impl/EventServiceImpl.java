package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.repository.EventRepository;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private UserInfoService userInfoService;
    private ModelMapperService modelMapperService;
    private SecurityService securityService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserInfoService userInfoService,
                            ModelMapperService modelMapperService, SecurityService securityService) {
        this.eventRepository = eventRepository;
        this.userInfoService = userInfoService;
        this.modelMapperService = modelMapperService;
        this.securityService = securityService;
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public Event create(EventDto.PostRequest createDto) {
        Event eventToSave = new Event();
        String usernameFromToken = securityService.getUsernameFromToken();
        eventToSave.setAuthor(
                userInfoService.findByUsername(usernameFromToken)
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, usernameFromToken)
                        ))
        );
        modelMapperService.map(createDto, eventToSave);
        return save(eventToSave);
    }

    @Override
    public Event update(Long id, EventDto.PostRequest updateDto) {
        Event eventToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, id)
        ));
        modelMapperService.map(updateDto, eventToUpdate);
        return save(eventToUpdate);
    }

}
