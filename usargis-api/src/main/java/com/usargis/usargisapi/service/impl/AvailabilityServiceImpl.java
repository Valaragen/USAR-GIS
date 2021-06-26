package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.core.search.AvailabilitySearchCriteria;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import com.usargis.usargisapi.web.exception.ProhibitedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;
    private UserInfoService userInfoService;
    private MissionService missionService;

    private ModelMapperService modelMapperService;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, UserInfoService userInfoService,
                                   MissionService missionService, ModelMapperService modelMapperService) {
        this.availabilityRepository = availabilityRepository;
        this.userInfoService = userInfoService;
        this.missionService = missionService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return availabilityRepository.findById(id);
    }

    @Override
    public Availability save(Availability availability) {
        checkValid(availability);
        return availabilityRepository.save(availability);
    }

    @Override
    public void delete(Availability availability) {
        availabilityRepository.delete(availability);
    }

    @Override
    public List<Availability> searchAll(AvailabilitySearchCriteria availabilitySearchCriteria) {
        return availabilityRepository.searchAll(availabilitySearchCriteria);
    }

    @Override
    public Availability create(AvailabilityDto.AvailabilityCreate createDto) {
        Availability availabilityToCreate = new Availability();
        availabilityToCreate.setUserInfo(
                userInfoService.findByUsername(createDto.getUserInfoUsername())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, createDto.getUserInfoUsername())
                        ))
        );
        availabilityToCreate.setMission(
                missionService.findById(createDto.getMissionId())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, createDto.getMissionId())
                        ))
        );
        modelMapperService.map(createDto, availabilityToCreate);
        return save(availabilityToCreate);
    }

    @Override
    public Availability update(Long id, AvailabilityDto.AvailabilityUpdate updateDto) {
        Availability availabilityToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, id)
        ));
        modelMapperService.map(updateDto, availabilityToUpdate);
        return save(availabilityToUpdate);
    }

    private void checkValid(Availability availability) {
        MissionStatus linkedMissionStatus = availability.getMission().getStatus();
        if ((linkedMissionStatus.equals(MissionStatus.ONFOCUS) || linkedMissionStatus.equals(MissionStatus.ONGOING) ||
                linkedMissionStatus.equals(MissionStatus.FINISHED) || linkedMissionStatus.equals(MissionStatus.CANCELLED))) {
            throw new ProhibitedActionException(
                    MessageFormat.format(ErrorConstant.AVAILABILITY_CANT_BE_CREATED_OR_UPDATED_WHEN_LINKED_MISSION_STATUS_IS,
                            linkedMissionStatus.getName())
            );
        }
    }
}
