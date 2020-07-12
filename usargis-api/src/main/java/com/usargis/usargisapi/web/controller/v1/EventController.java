package com.usargis.usargisapi.web.controller.v1;

import com.usargis.usargisapi.model.Event;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constant.V1_PATH)
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(Constant.EVENTS_PATH)
    public ResponseEntity<List<Event>> findAllEvents() {
        List<Event> events = eventService.findAll();
        if (events.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_EVENTS_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.FOUND);
    }

    @GetMapping(Constant.EVENTS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.findById(id);
        Event result = event.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping(Constant.EVENTS_PATH)
    public ResponseEntity<Event> createNewEvent(@RequestBody Event event) {
        //TODO implement
        Event result = eventService.save(event);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.EVENTS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        eventService.delete(eventService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
