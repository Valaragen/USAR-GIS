package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.repository.TeamMemberRepository;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamMemberService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private TeamMemberRepository teamMemberRepository;
    private UserInfoService userInfoService;
    private TeamService teamService;
    private ModelMapperService modelMapperService;

    @Autowired
    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository, UserInfoService userInfoService,
                                 TeamService teamService, ModelMapperService modelMapperService) {
        this.teamMemberRepository = teamMemberRepository;
        this.userInfoService = userInfoService;
        this.teamService = teamService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<TeamMember> findAll() {
        return teamMemberRepository.findAll();
    }

    @Override
    public Optional<TeamMember> findById(Long id) {
        return teamMemberRepository.findById(id);
    }

    @Override
    public TeamMember save(TeamMember teamMember) {
        return teamMemberRepository.save(teamMember);
    }

    @Override
    public void delete(TeamMember teamMember) {
        teamMemberRepository.delete(teamMember);
    }

    @Override
    public TeamMember create(TeamMemberDto.PostRequest createDto) {
        TeamMember teamMemberToCreate = new TeamMember();
        teamMemberToCreate.setUserInfo(
                userInfoService.findByUsername(createDto.getUserInfoUsername())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, createDto.getUserInfoUsername())
                        ))
        );
        teamMemberToCreate.setTeam(
                teamService.findById(createDto.getTeamId())
                        .orElseThrow(() -> new NotFoundException(
                                MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, createDto.getTeamId())
                        ))
        );
        modelMapperService.map(createDto, teamMemberToCreate);
        return save(teamMemberToCreate);
    }

    @Override
    public TeamMember update(Long id, TeamMemberDto.PostRequest updateDto) {
        TeamMember teamMemberToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_TEAM_MEMBER_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, teamMemberToUpdate);
        return save(teamMemberToUpdate);
    }
}
