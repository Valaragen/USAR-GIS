package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.*;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import com.usargis.usargisapi.web.exception.ProhibitedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;
    private UserInfoService userInfoService;
    private MissionService missionService;
    private SecurityService securityService;

    private ModelMapperService modelMapperService;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, UserInfoService userInfoService,
                                   MissionService missionService, SecurityService securityService,
                                   ModelMapperService modelMapperService) {
        this.availabilityRepository = availabilityRepository;
        this.userInfoService = userInfoService;
        this.missionService = missionService;
        this.securityService = securityService;
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
    public List<Availability> searchAll(AvailabilitySearch availabilitySearch) {
        return availabilityRepository.searchAll(availabilitySearch);
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

    private void checkValid(Availability availabilityToSave) {
        MissionStatus linkedMissionStatus = availabilityToSave.getMission().getStatus();
        if ((linkedMissionStatus.equals(MissionStatus.ONFOCUS) || linkedMissionStatus.equals(MissionStatus.ONGOING) ||
                linkedMissionStatus.equals(MissionStatus.FINISHED) || linkedMissionStatus.equals(MissionStatus.CANCELLED))) {
            throw new ProhibitedActionException(
                    MessageFormat.format(ErrorConstant.AVAILABILITY_CANT_BE_CREATED_OR_UPDATED_WHEN_LINKED_MISSION_STATUS_IS,
                            linkedMissionStatus.getName())
            );
        }

        if (availabilityToSave.getStartDate() != null && availabilityToSave.getEndDate() != null) {

            //Start date can't be before end date
            if (availabilityToSave.getStartDate().isAfter(availabilityToSave.getEndDate())) {
                throw new ProhibitedActionException(ErrorConstant.AVAILABILITY_START_DATE_CANT_BE_AFTER_END_DATE);
            } else if (availabilityToSave.getStartDate().equals(availabilityToSave.getEndDate())) {
                throw new ProhibitedActionException(ErrorConstant.AVAILABILITY_START_DATE_CANT_BE_EQUAL_TO_END_DATE);
            }

            //Already covered by another availability
            AvailabilitySearch availabilitySearch = new AvailabilitySearch();
            availabilitySearch.setUserUsername(availabilityToSave.getUserInfo().getUsername());
            availabilitySearch.setMissionId(availabilityToSave.getMission().getId());
            List<Availability> currentAvailabilitiesOnMission = searchAll(availabilitySearch);
            if (availabilityToSave.getId() != null) {
                currentAvailabilitiesOnMission = currentAvailabilitiesOnMission.stream().filter((availability) -> !availability.getId().equals(availabilityToSave.getId())).collect(Collectors.toList());
            }
            currentAvailabilitiesOnMission.forEach((availability) -> {
                if ((availabilityToSave.getStartDate().isAfter(availability.getStartDate()) && availabilityToSave.getStartDate().isBefore(availability.getEndDate()))
                        || (availabilityToSave.getEndDate().isAfter(availability.getStartDate()) && availabilityToSave.getEndDate().isBefore(availability.getEndDate()))
                        || (availabilityToSave.getStartDate().isBefore(availability.getStartDate()) && availabilityToSave.getEndDate().isAfter(availability.getEndDate()))
                        || (availabilityToSave.getStartDate().equals(availability.getStartDate()) || availabilityToSave.getEndDate().isEqual(availability.getEndDate()))) {
                    throw new ProhibitedActionException(
                            MessageFormat.format(ErrorConstant.AVAILABILITY_ALREADY_COVERED_BY_THE_AVAILABILITY_OF_ID_WITH_START_DATE_AND_END_DATE,
                                    availability.getId(), availability.getStartDate(), availability.getEndDate())
                    );
                }
            });
        }
    }

    @Override
    public boolean isAvailabilityOwner(Long id) {
        Optional<Availability> availability = findById(id);
        return availability.filter(value -> securityService.isSameUsernameThanAuthenticatedUser(value.getUserInfo().getUsername())).isPresent();
    }
}
