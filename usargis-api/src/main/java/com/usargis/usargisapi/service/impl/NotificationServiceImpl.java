package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.repository.NotificationRepository;
import com.usargis.usargisapi.service.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserInfoService userInfoService;
    private EventService eventService;
    private MissionService missionService;
    private SecurityService securityService;
    private ModelMapperService modelMapperService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserInfoService userInfoService,
                                   EventService eventService, MissionService missionService, SecurityService securityService,
                                   ModelMapperService modelMapperService) {
        this.notificationRepository = notificationRepository;
        this.userInfoService = userInfoService;
        this.eventService = eventService;
        this.missionService = missionService;
        this.securityService = securityService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Override
    public Notification create(NotificationDto.PostRequest createDto) {
        return null;
    }

    @Override
    public Notification update(Long id, NotificationDto.PostRequest updateDto) {
        return null;
    }
}
