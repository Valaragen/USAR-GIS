package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.repository.TeamRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.TeamDtoMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TeamServiceImplTest {

    private TeamService objectToTest;

    private TeamRepository teamRepository = Mockito.mock(TeamRepository.class);
    private MissionService missionService = Mockito.mock(MissionService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamServiceImpl(teamRepository, missionService, modelMapperService);
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

    @Nested
    class createTest {
        private TeamDto.TeamPostRequest teamPostRequestDto = TeamDtoMother.postRequestSample().build();
        private Mission missionToLink = MissionMother.sampleFinished().build();
        private Team savedTeam = TeamMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(missionService.findById(teamPostRequestDto.getMissionId())).thenReturn(Optional.of(missionToLink));
            Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenReturn(savedTeam);
        }

        @Test
        void create_noMissionForGivenMissionId_throwNotFoundException() {
            Mockito.when(missionService.findById(teamPostRequestDto.getMissionId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.create(teamPostRequestDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, teamPostRequestDto.getMissionId()));
        }

        @Test
        void create_shouldMapDtoInTeam() {
            objectToTest.create(teamPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(TeamDto.class), Mockito.any(Team.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(teamPostRequestDto);

            Mockito.verify(teamRepository).save(Mockito.any(Team.class));
        }

        @Test
        void create_shouldReturnSavedTeam() {
            Team result = objectToTest.create(teamPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedTeam);
        }

        @Test
        void create_returnedTeamShouldContainLinkedEntities() {
            Mockito.when(teamRepository.save(Mockito.any(Team.class))).then(AdditionalAnswers.returnsFirstArg());

            Team result = objectToTest.create(teamPostRequestDto);

            Assertions.assertThat(result.getMission()).isEqualTo(missionToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Team teamToUpdate = TeamMother.sample().build();
        private TeamDto.TeamPostRequest teamUpdateDto = TeamDtoMother.postRequestSample().build();
        private Team savedTeam = TeamMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(teamRepository.findById(givenId)).thenReturn(Optional.ofNullable(teamToUpdate));
            Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenReturn(savedTeam);
        }

        @Test
        void update_noTeamForGivenId_throwNotFoundException() {
            Mockito.when(teamRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.update(givenId, teamUpdateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInTeam() {
            objectToTest.update(givenId, teamUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(TeamDto.class), Mockito.any(Team.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, teamUpdateDto);

            Mockito.verify(teamRepository).save(Mockito.any(Team.class));
        }

        @Test
        void update_shouldReturnSavedTeam() {
            Team result = objectToTest.update(givenId, teamUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedTeam);
        }
    }
}
