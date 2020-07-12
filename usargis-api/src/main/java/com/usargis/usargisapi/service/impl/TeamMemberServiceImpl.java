package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.TeamMember;
import com.usargis.usargisapi.model.embeddable.TeamMemberId;
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
    public Optional<TeamMember> findById(TeamMemberId id) {
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
}
