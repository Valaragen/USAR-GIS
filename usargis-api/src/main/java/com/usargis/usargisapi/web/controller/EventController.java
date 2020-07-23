package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.EventDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EventController implements ApiRestController {

    private EventService eventService;
    private ModelMapperService modelMapperService;

    @Autowired
    public EventController(EventService eventService, ModelMapperService modelMapperService) {
        this.eventService = eventService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.EVENTS_PATH)
    public ResponseEntity<List<EventDto.Response>> findAllEvents() {
        List<Event> events = eventService.findAll();
        if (events.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_EVENT_FOUND);
        }
        return new ResponseEntity<>(events.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.EVENTS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<EventDto.Response> getEventById(@PathVariable Long id) {
        Optional<Event> eventOptional = eventService.findById(id);
        Event event = eventOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(event), HttpStatus.OK);
    }

    @PostMapping(Constant.EVENTS_PATH)
    public ResponseEntity<EventDto.Response> createNewEvent(@RequestBody @Valid EventDto.PostRequest eventCreateDto) {
        Event event = eventService.create(eventCreateDto);
        return new ResponseEntity<>(convertToResponseDto(event), HttpStatus.CREATED);
    }

    @PutMapping(Constant.EVENTS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<EventDto.Response> updateEvent(@PathVariable Long id, @RequestBody @Valid EventDto.PostRequest updateDto) {
        Event event = eventService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(event), HttpStatus.OK);
    }

    @DeleteMapping(Constant.EVENTS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        eventService.delete(eventService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EventDto.Response convertToResponseDto(Event event) {
        return modelMapperService.map(event, EventDto.Response.class);
    }

}
