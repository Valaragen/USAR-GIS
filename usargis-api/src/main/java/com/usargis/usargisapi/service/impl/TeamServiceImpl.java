package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.repository.TeamRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private MissionService missionService;
    private ModelMapperService modelMapperService;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, MissionService missionService,
                           ModelMapperService modelMapperService) {
        this.teamRepository = teamRepository;
        this.missionService = missionService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public Team create(TeamDto.TeamPostRequest createDto) {
        Team teamToCreate = new Team();
        teamToCreate.setMission(
                missionService.findById(createDto.getMissionId())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, createDto.getMissionId())
                        ))
        );
        modelMapperService.map(createDto, teamToCreate);
        return save(teamToCreate);
    }

    @Override
    public Team update(Long id, TeamDto.TeamPostRequest updateDto) {
        Team teamToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, teamToUpdate);
        return save(teamToUpdate);
    }
}
