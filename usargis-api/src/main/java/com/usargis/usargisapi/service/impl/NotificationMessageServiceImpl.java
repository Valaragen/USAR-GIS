package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.NotificationMessage;
import com.usargis.usargisapi.model.embeddable.NotificationMessageId;
import com.usargis.usargisapi.repository.NotificationMessageRepository;
import com.usargis.usargisapi.service.contract.NotificationMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {

    private NotificationMessageRepository notificationMessageRepository;

    @Autowired
    public NotificationMessageServiceImpl(NotificationMessageRepository notificationMessageRepository) {
        this.notificationMessageRepository = notificationMessageRepository;
    }

    @Override
    public List<NotificationMessage> findAll() {
        return notificationMessageRepository.findAll();
    }

    @Override
    public Optional<NotificationMessage> findById(NotificationMessageId id) {
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
}
