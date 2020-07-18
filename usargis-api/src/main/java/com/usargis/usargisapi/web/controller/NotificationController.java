package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.model.Notification;
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

@PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
@RestController
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(Constant.NOTIFICATIONS_PATH)
    public ResponseEntity<List<Notification>> findAllNotifications() {
        List<Notification> notifications = notificationService.findAll();
        if (notifications.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_NOTIFICATIONS_FOUND);
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(Constant.NOTIFICATIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        Notification result = notification.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(Constant.NOTIFICATIONS_PATH)
    public ResponseEntity<Notification> createNewNotification(@RequestBody Notification notification) {
        //TODO implement
        Notification result = notificationService.save(notification);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.NOTIFICATIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Notification> deleteNotification(@PathVariable Long id) {
        notificationService.delete(notificationService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
