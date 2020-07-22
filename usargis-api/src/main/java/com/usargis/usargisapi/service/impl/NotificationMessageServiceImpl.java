package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.repository.NotificationMessageRepository;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import com.usargis.usargisapi.service.contract.NotificationService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {

    private NotificationMessageRepository notificationMessageRepository;
    private NotificationService notificationService;
    private ModelMapperService modelMapperService;

    @Autowired
    public NotificationMessageServiceImpl(NotificationMessageRepository notificationMessageRepository,
                                          NotificationService notificationService, ModelMapperService modelMapperService) {
        this.notificationMessageRepository = notificationMessageRepository;
        this.notificationService = notificationService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<NotificationMessage> findAll() {
        return notificationMessageRepository.findAll();
    }

    @Override
    public Optional<NotificationMessage> findById(Long id) {
        return notificationMessageRepository.findById(id);
    }

    @Override
    public NotificationMessage save(NotificationMessage notificationMessage) {
        return notificationMessageRepository.save(notificationMessage);
    }

    @Override
    public void delete(NotificationMessage notificationMessage) {
        notificationMessageRepository.delete(notificationMessage);
    }

    @Override
    public NotificationMessage create(NotificationMessageDto.PostRequest createDto) {
        NotificationMessage notificationMessageToSave = new NotificationMessage();
        notificationMessageToSave.setNotification(
                notificationService.findById(createDto.getNotificationId())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, createDto.getNotificationId())
                        ))
        );
        modelMapperService.map(createDto, notificationMessageToSave);
        return save(notificationMessageToSave);
    }

    @Override
    public NotificationMessage update(Long id, NotificationMessageDto.PostRequest updateDto) {
        NotificationMessage notificationMessageToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                MessageFormat.format(ErrorConstant.NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID, id)
        ));
        modelMapperService.map(updateDto, notificationMessageToUpdate);
        return save(notificationMessageToUpdate);
    }
}
