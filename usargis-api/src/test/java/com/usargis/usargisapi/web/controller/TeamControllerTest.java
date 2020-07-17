package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TeamControllerTest {

    private TeamController objectToTest;

    private TeamService teamService = Mockito.mock(TeamService.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamController(teamService);
    }

    @Nested
    class findAllTeamsTest {
        @Test
        void findAllTeams_shouldCallServiceLayer() {
            Mockito.when(teamService.findAll()).thenReturn(Collections.singletonList(new Team()));

            objectToTest.findAllTeams();

            Mockito.verify(teamService).findAll();
        }

        @Test
        void findAllTeams_noTeamFound_throwNotFoundException() {
            Mockito.when(teamService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllTeams())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_TEAMS_FOUND);
        }

        @Test
        void findAllTeams_teamFound_returnResponseEntityWithTeamList() {
            List<Team> returnedTeamList = Collections.singletonList(new Team());
            Mockito.when(teamService.findAll()).thenReturn(returnedTeamList);

            ResponseEntity result = objectToTest.findAllTeams();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedTeamList);
        }
    }

    @Nested
    class getTeamByIdTest {
        private final Long teamIdToFind = 1L;
        private final Team teamFound = new Team();

        @Test
        void getTeamById_shouldCallServiceLayer() {
            Mockito.when(teamService.findById(teamIdToFind)).thenReturn(Optional.of(teamFound));

            objectToTest.getTeamById(teamIdToFind);

            Mockito.verify(teamService).findById(teamIdToFind);
        }

        @Test
        void getTeamById_noTeamFound_throwNotFoundException() {
            Mockito.when(teamService.findById(teamIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getTeamById(teamIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, teamIdToFind));
        }

        @Test
        void getTeamById_teamFound_returnResponseEntityWithTeamAndStatusOk() {
            Mockito.when(teamService.findById(teamIdToFind)).thenReturn(Optional.of(teamFound));

            ResponseEntity result = objectToTest.getTeamById(teamIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamFound);
        }
    }

    @Nested
    class createNewTeamTest {
        private final Team teamToSave = new Team();
        private final Team savedTeam = new Team();

        @Test
        void createNewTeam_shouldCallServiceLayer() {
            Mockito.when(teamService.save(teamToSave)).thenReturn(savedTeam);

            objectToTest.createNewTeam(teamToSave);

            Mockito.verify(teamService).save(teamToSave);
        }

        @Test
        void createNewTeam_teamCreated_returnResponseEntityWithTeamAndStatusCreated() {
            Mockito.when(teamService.save(teamToSave)).thenReturn(savedTeam);

            ResponseEntity result = objectToTest.createNewTeam(teamToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedTeam);
        }
    }

    @Nested
    class deleteTeamTest {
        private final Long teamToDeleteId = 1L;
        private final Team foundTeamToDelete = new Team();

        @Test
        void deleteTeam_shouldCallServiceLayer() {
            Mockito.when(teamService.findById(teamToDeleteId)).thenReturn(Optional.of(foundTeamToDelete));
            Mockito.doNothing().when(teamService).delete(foundTeamToDelete);

            objectToTest.deleteTeam(teamToDeleteId);

            Mockito.verify(teamService).findById(teamToDeleteId);
            Mockito.verify(teamService).delete(foundTeamToDelete);
        }

        @Test
        void deleteTeam_teamDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(teamService.findById(teamToDeleteId)).thenReturn(Optional.of(foundTeamToDelete));
            Mockito.doNothing().when(teamService).delete(foundTeamToDelete);

            ResponseEntity result = objectToTest.deleteTeam(teamToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(null);
        }
    }

}
