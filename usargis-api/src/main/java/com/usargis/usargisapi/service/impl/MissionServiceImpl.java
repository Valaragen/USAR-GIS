package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.repository.MissionRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionService {

    private MissionRepository missionRepository;
    private UserInfoService userInfoService;
    private ModelMapperService modelMapperService;
    private SecurityService securityService;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository, UserInfoService userInfoService,
                              ModelMapperService modelMapperService, SecurityService securityService) {
        this.missionRepository = missionRepository;
        this.userInfoService = userInfoService;
        this.modelMapperService = modelMapperService;
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
        return missionRepository.save(mission);
    }

    @Override
    public void delete(Mission mission) {
        missionRepository.delete(mission);
    }

    @Override
    public Mission create(MissionDto.PostRequest createDto) {
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
    public Mission update(Long id, MissionDto.PostRequest updateDto) {
        Mission missionToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, missionToUpdate);
        return save(missionToUpdate);
    }
}
