package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.NotificationDto;
import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.repository.NotificationRepository;
import com.usargis.usargisapi.service.contract.*;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
    public Notification create(NotificationDto.NotificationPostRequest createDto) {
        Notification notificationToCreate = new Notification();
        String usernameFromToken = securityService.getUsernameFromToken();
        notificationToCreate.setAuthor(
                userInfoService.findByUsername(usernameFromToken)
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, usernameFromToken)
                        ))
        );
        if (createDto.getMissionId() != null) {
            notificationToCreate.setMission(
                    missionService.findById(createDto.getMissionId())
                            .orElseThrow(() -> new NotFoundException(
                                    MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, createDto.getMissionId())
                            ))
            );
        }
        if (createDto.getEventId() != null) {
            notificationToCreate.setEvent(
                    eventService.findById(createDto.getEventId())
                            .orElseThrow(() -> new NotFoundException(
                                    MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, createDto.getEventId())
                            ))
            );
        }
        modelMapperService.map(createDto, notificationToCreate);
        return save(notificationToCreate);
    }

    @Override
    public Notification update(Long id, NotificationDto.NotificationPostRequest updateDto) {
        Notification notificationToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_NOTIFICATION_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, notificationToUpdate);
        return save(notificationToUpdate);
    }
}
