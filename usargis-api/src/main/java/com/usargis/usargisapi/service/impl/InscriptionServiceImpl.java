package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.repository.InscriptionRepository;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.InscriptionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    private InscriptionRepository inscriptionRepository;
    private UserInfoService userInfoService;
    private EventService eventService;
    private ModelMapperService modelMapperService;

    @Autowired
    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository, UserInfoService userInfoService,
                                  EventService eventService, ModelMapperService modelMapperService) {
        this.inscriptionRepository = inscriptionRepository;
        this.userInfoService = userInfoService;
        this.eventService = eventService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<Inscription> findAll() {
        return inscriptionRepository.findAll();
    }

    @Override
    public Optional<Inscription> findById(Long id) {
        return inscriptionRepository.findById(id);
    }

    @Override
    public Inscription save(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public void delete(Inscription inscription) {
        inscriptionRepository.delete(inscription);
    }

    @Override
    public Inscription create(InscriptionDto.PostRequest createDto) {
        Inscription inscriptionToSave = new Inscription();
        inscriptionToSave.setUserInfo(
                userInfoService.findByUsername(createDto.getUserInfoUsername())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, createDto.getUserInfoUsername())
                        ))
        );
        inscriptionToSave.setEvent(
                eventService.findById(createDto.getEventId())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, createDto.getEventId())
                        ))
        );
        modelMapperService.map(createDto, inscriptionToSave);
        return save(inscriptionToSave);
    }

    @Override
    public Inscription update(Long id, InscriptionDto.PostRequest updateDto) {
        Inscription inscriptionToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, id)
        ));
        modelMapperService.map(updateDto, inscriptionToUpdate);
        return save(inscriptionToUpdate);
    }
}
