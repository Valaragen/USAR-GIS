package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
@RestController
public class NotificationController {

    private NotificationService notificationService;
    private ModelMapperService modelMapperService;

    @Autowired
    public NotificationController(NotificationService notificationService, ModelMapperService modelMapperService) {
        this.notificationService = notificationService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.NOTIFICATIONS_PATH)
    public ResponseEntity<List<NotificationDto.Response>> findAllNotifications() {
        List<Notification> notifications = notificationService.findAll();
        if (notifications.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_NOTIFICATIONS_FOUND);
        }
        return new ResponseEntity<>(notifications.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.NOTIFICATIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<NotificationDto.Response> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notificationOptional = notificationService.findById(id);
        Notification notification = notificationOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(notification), HttpStatus.OK);
    }

    @PostMapping(Constant.NOTIFICATIONS_PATH)
    public ResponseEntity<NotificationDto.Response> createNewNotification(@RequestBody NotificationDto.PostRequest notificationCreateDto) {
        Notification notification = notificationService.create(notificationCreateDto);
        return new ResponseEntity<>(convertToResponseDto(notification), HttpStatus.CREATED);
    }

    @PutMapping(Constant.NOTIFICATIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<NotificationDto.Response> updateNotification(@PathVariable Long id, @RequestBody NotificationDto.PostRequest updateDto) {
        Notification notification = notificationService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(notification), HttpStatus.OK);
    }

    @DeleteMapping(Constant.NOTIFICATIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteNotification(@PathVariable Long id) {
        notificationService.delete(notificationService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private NotificationDto.Response convertToResponseDto(Notification notification) {
        return modelMapperService.map(notification, NotificationDto.Response.class);
    }
}
