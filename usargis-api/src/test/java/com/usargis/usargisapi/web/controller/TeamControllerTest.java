package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.TeamDtoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.*;

class TeamControllerTest {
    private TeamController objectToTest;

    private TeamService teamService = Mockito.mock(TeamService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamController(teamService, modelMapperService);
    }

    @Nested
    class findAllTeamsTest {
        private final List<Team> teamsFound = Arrays.asList(new Team(), new Team());

        @Test
        void findAllTeams_shouldCallServiceLayer() {
            Mockito.when(teamService.findAll()).thenReturn(teamsFound);

            objectToTest.findAllTeams();

            Mockito.verify(teamService).findAll();
        }

        @Test
        void findAllTeams_noTeamFound_throwNotFoundException() {
            Mockito.when(teamService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllTeams())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_TEAM_FOUND);
        }

        @Test
        void findAllTeams_shouldConvertTeamsToListOfResponseDto() {
            Mockito.when(teamService.findAll()).thenReturn(teamsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Team.class), Mockito.any())).thenReturn(new TeamDto.Response());

            objectToTest.findAllTeams();

            Mockito.verify(modelMapperService, Mockito.times(teamsFound.size())).map(Mockito.any(Team.class), Mockito.any());
        }

        @Test
        void findAllTeams_teamFound_returnStatusOkWithListOfTeamsResponseDto() {
            Mockito.when(teamService.findAll()).thenReturn(teamsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Team.class), Mockito.any())).thenReturn(new TeamDto.Response());

            ResponseEntity<List<TeamDto.Response>> result = objectToTest.findAllTeams();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(teamsFound.size());
        }
    }

    @Nested
    class getTeamByIdTest {
        private final Long teamIdToFind = 1L;
        private final Team teamFound = new Team();
        private final TeamDto.Response teamResponseDto = new TeamDto.Response();

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
        void getTeamById_shouldConvertTeamToResponseDto() {
            Mockito.when(teamService.findById(teamIdToFind)).thenReturn(Optional.of(teamFound));
            Mockito.when(modelMapperService.map(teamFound, TeamDto.Response.class)).thenReturn(teamResponseDto);

            objectToTest.getTeamById(teamIdToFind);

            Mockito.verify(modelMapperService).map(teamFound, TeamDto.Response.class);
        }

        @Test
        void getTeamById_teamFound_returnStatusOkAndTeamResponseDto() {
            Mockito.when(teamService.findById(teamIdToFind)).thenReturn(Optional.of(teamFound));
            Mockito.when(modelMapperService.map(teamFound, TeamDto.Response.class)).thenReturn(teamResponseDto);

            ResponseEntity<TeamDto.Response> result = objectToTest.getTeamById(teamIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamDto.Response.class);
        }
    }

    @Nested
    class createNewTeamTest {
        private final TeamDto.PostRequest teamToSave = TeamDtoMother.postRequestSample().build();
        private final Team newTeam = new Team();
        private final TeamDto.Response teamResponseDto = new TeamDto.Response();

        @Test
        void createNewTeam_shouldCallServiceLayer() {
            Mockito.when(teamService.create(teamToSave)).thenReturn(newTeam);

            objectToTest.createNewTeam(teamToSave);

            Mockito.verify(teamService).create(teamToSave);
        }

        @Test
        void createNewTeam_shouldConvertTeamToResponseDto() {
            Mockito.when(teamService.create(teamToSave)).thenReturn(newTeam);
            Mockito.when(modelMapperService.map(newTeam, TeamDto.Response.class)).thenReturn(teamResponseDto);

            objectToTest.createNewTeam(teamToSave);

            Mockito.verify(modelMapperService).map(newTeam, TeamDto.Response.class);
        }

        @Test
        void createNewTeam_teamCreated_returnStatusCreatedAndTeamResponseDto() {
            Mockito.when(teamService.create(teamToSave)).thenReturn(newTeam);
            Mockito.when(modelMapperService.map(newTeam, TeamDto.Response.class)).thenReturn(teamResponseDto);

            ResponseEntity<TeamDto.Response> result = objectToTest.createNewTeam(teamToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamDto.Response.class);
        }
    }

    @Nested
    class updateTeamTest {
        private final Long teamId = 1L;
        private final TeamDto.PostRequest teamToUpdate = TeamDtoMother.postRequestSample().build();
        private final Team updateTeam = new Team();
        private final TeamDto.Response teamResponseDto = new TeamDto.Response();

        @Test
        void updateTeamTest_shouldCallServiceLayer() {
            Mockito.when(teamService.update(teamId, teamToUpdate)).thenReturn(updateTeam);

            objectToTest.updateTeam(teamId, teamToUpdate);

            Mockito.verify(teamService).update(teamId, teamToUpdate);
        }

        @Test
        void updateTeamTest_shouldConvertTeamToResponseDto() {
            Mockito.when(teamService.update(teamId, teamToUpdate))
                    .thenReturn(updateTeam);
            Mockito.when(modelMapperService.map(updateTeam, TeamDto.Response.class))
                    .thenReturn(teamResponseDto);

            objectToTest.updateTeam(teamId, teamToUpdate);

            Mockito.verify(modelMapperService).map(updateTeam, TeamDto.Response.class);
        }

        @Test
        void updateTeamTest_teamCreated_returnStatusOkAndTeamResponseDto() {
            Mockito.when(teamService.update(teamId, teamToUpdate))
                    .thenReturn(updateTeam);
            Mockito.when(modelMapperService.map(updateTeam, TeamDto.Response.class))
                    .thenReturn(teamResponseDto);

            ResponseEntity<TeamDto.Response> result =
                    objectToTest.updateTeam(teamId, teamToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamDto.Response.class);
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
                    .isNull();
        }
    }
}
