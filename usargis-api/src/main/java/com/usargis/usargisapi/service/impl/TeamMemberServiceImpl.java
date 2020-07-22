package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.repository.TeamMemberRepository;
import com.usargis.usargisapi.service.contract.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
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
        return null;
    }

    @Override
    public TeamMember update(Long id, TeamMemberDto.PostRequest updateDto) {
        return null;
    }
}
