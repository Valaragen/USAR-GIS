package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class NotificationMessageController implements ApiRestController {

    private NotificationMessageService notificationMessageService;
    private ModelMapperService modelMapperService;

    @Autowired
    public NotificationMessageController(NotificationMessageService notificationMessageService, ModelMapperService modelMapperService) {
        this.notificationMessageService = notificationMessageService;
        this.modelMapperService = modelMapperService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.NOTIFICATION_MESSAGES_PATH)
    public ResponseEntity<List<NotificationMessageDto.NotificationMessageResponse>> findAllNotificationMessages() {
        List<NotificationMessage> notificationMessages = notificationMessageService.findAll();
        if (notificationMessages.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND);
        }
        return new ResponseEntity<>(notificationMessages.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.NOTIFICATION_MESSAGES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<NotificationMessageDto.NotificationMessageResponse> getNotificationMessageById(@PathVariable Long id) {
        Optional<NotificationMessage> notificationMessageOptional = notificationMessageService.findById(id);
        NotificationMessage notificationMessage = notificationMessageOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(notificationMessage), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.NOTIFICATION_MESSAGES_PATH)
    public ResponseEntity<NotificationMessageDto.NotificationMessageResponse> createNewNotificationMessage(@RequestBody @Valid NotificationMessageDto.NotificationMessagePostRequest notificationMessageCreateDto) {
        NotificationMessage notificationMessage = notificationMessageService.create(notificationMessageCreateDto);
        return new ResponseEntity<>(convertToResponseDto(notificationMessage), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PutMapping(Constant.NOTIFICATION_MESSAGES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<NotificationMessageDto.NotificationMessageResponse> updateNotificationMessage(@PathVariable Long id, @RequestBody @Valid NotificationMessageDto.NotificationMessagePostRequest updateDto) {
        NotificationMessage notificationMessage = notificationMessageService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(notificationMessage), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @DeleteMapping(Constant.NOTIFICATION_MESSAGES_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteNotificationMessage(@PathVariable Long id) {
        notificationMessageService.delete(notificationMessageService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private NotificationMessageDto.NotificationMessageResponse convertToResponseDto(NotificationMessage notificationMessage) {
        return modelMapperService.map(notificationMessage, NotificationMessageDto.NotificationMessageResponse.class);
    }

}
