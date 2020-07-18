package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;
import com.usargis.usargisapi.repository.TeamMemberRepository;
import com.usargis.usargisapi.service.contract.TeamMemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TeamMemberServiceImplTest {

    private TeamMemberService objectToTest;

    private TeamMemberRepository teamMemberRepository = Mockito.mock(TeamMemberRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamMemberServiceImpl(teamMemberRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<TeamMember> returnedTeamMemberList = Collections.singletonList(new TeamMember());
        Mockito.when(teamMemberRepository.findAll()).thenReturn(returnedTeamMemberList);

        List<TeamMember> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedTeamMemberList);
        Mockito.verify(teamMemberRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        TeamMember teamMemberFound = new TeamMember();
        Optional<TeamMember> expectedResult = Optional.of(teamMemberFound);
        TeamMemberId teamMemberIdToFind = new TeamMemberId();
        Mockito.when(teamMemberRepository.findById(teamMemberIdToFind)).thenReturn(expectedResult);

        Optional<TeamMember> result = objectToTest.findById(teamMemberIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(teamMemberRepository).findById(teamMemberIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnTeamMember() {
        TeamMember teamMemberToSave = new TeamMember();
        Mockito.when(teamMemberRepository.save(teamMemberToSave)).thenReturn(teamMemberToSave);

        TeamMember result = objectToTest.save(teamMemberToSave);

        Assertions.assertThat(result).isEqualTo(teamMemberToSave);
        Mockito.verify(teamMemberRepository).save(teamMemberToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        TeamMember teamMemberToDelete = new TeamMember();
        Mockito.doNothing().when(teamMemberRepository).delete(teamMemberToDelete);

        objectToTest.delete(teamMemberToDelete);

        Mockito.verify(teamMemberRepository).delete(teamMemberToDelete);
    }

}
