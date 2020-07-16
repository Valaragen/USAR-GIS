package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.repository.TeamRepository;
import com.usargis.usargisapi.service.contract.TeamService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TeamServiceImplTest {

    private TeamService objectToTest;

    private TeamRepository teamRepository = Mockito.mock(TeamRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamServiceImpl(teamRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Team> returnedTeamList = Collections.singletonList(new Team());
        Mockito.when(teamRepository.findAll()).thenReturn(returnedTeamList);

        List<Team> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedTeamList);
        Mockito.verify(teamRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Team teamFound = new Team();
        Optional<Team> expectedResult = Optional.of(teamFound);
        Long teamIdToFind = 1L;
        Mockito.when(teamRepository.findById(teamIdToFind)).thenReturn(expectedResult);

        Optional<Team> result = objectToTest.findById(teamIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(teamRepository).findById(teamIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnTeam() {
        Team teamToSave = new Team();
        Mockito.when(teamRepository.save(teamToSave)).thenReturn(teamToSave);

        Team result = objectToTest.save(teamToSave);

        Assertions.assertThat(result).isEqualTo(teamToSave);
        Mockito.verify(teamRepository).save(teamToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Team teamToDelete = new Team();
        Mockito.doNothing().when(teamRepository).delete(teamToDelete);

        objectToTest.delete(teamToDelete);

        Mockito.verify(teamRepository).delete(teamToDelete);
    }

}
