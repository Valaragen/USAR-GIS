package com.usargis.usargisapi.service.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.repository.MissionRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import com.usargis.usargisapi.web.exception.ProhibitedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionService {

    private MissionRepository missionRepository;
    private UserInfoService userInfoService;
    private ModelMapperService modelMapperService;
    private SecurityService securityService;
    private ObjectMapper objectMapper;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository, UserInfoService userInfoService,
                              ModelMapperService modelMapperService, ObjectMapper objectMapper, SecurityService securityService) {
        this.missionRepository = missionRepository;
        this.userInfoService = userInfoService;
        this.modelMapperService = modelMapperService;
        this.objectMapper = objectMapper;
        this.securityService = securityService;
    }

    @Override
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public Optional<Mission> findById(Long id) {
        return missionRepository.findById(id);
    }

    @Override
    public Mission save(Mission mission) {
        checkValid(mission);
        return missionRepository.save(mission);
    }

    @Override
    public void delete(Mission mission) {
        missionRepository.delete(mission);
    }

    @Override
    public Mission create(MissionDto.MissionPostRequest createDto) {
        Mission missionToCreate = new Mission();
        String usernameFromToken = securityService.getUsernameFromToken();
        missionToCreate.setAuthor(
                userInfoService.findByUsername(usernameFromToken)
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, usernameFromToken)
                        ))
        );
        modelMapperService.map(createDto, missionToCreate);
        return save(missionToCreate);
    }

    @Override
    public Mission update(Long id, MissionDto.MissionPostRequest updateDto) {
        Mission missionToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, missionToUpdate);
        return save(missionToUpdate);
    }

    @Override
    public Mission patch(Long id, JsonPatch patchDocument) throws JsonPatchException {
        Mission missionToPatch = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)
                )
        );

        MissionDto.MissionPostRequest missionPostRequest = modelMapperService.map(missionToPatch, MissionDto.MissionPostRequest.class);
        JsonNode postRequestNode = objectMapper.valueToTree(missionPostRequest);
        postRequestNode = patchDocument.apply(postRequestNode);
        modelMapperService.map(objectMapper.convertValue(postRequestNode, MissionDto.MissionPostRequest.class), missionToPatch);

        return save(missionToPatch);
    }

    private void checkValid(Mission mission) {
        if ((mission.getStatus().equals(MissionStatus.ONGOING) || mission.getStatus().equals(MissionStatus.FINISHED))) {
            if (mission.getStartDate() == null) {
                throw new ProhibitedActionException(
                        MessageFormat.format(ErrorConstant.START_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS, mission.getStatus())
                );
            } else if (mission.getEndDate() == null) {
                throw new ProhibitedActionException(
                        MessageFormat.format(ErrorConstant.END_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS, mission.getStatus())
                );
            }
        }
    }
}
