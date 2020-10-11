package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.TeamMemberRepository;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamMemberService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.TeamMemberDtoMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMemberMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
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

class TeamMemberServiceImplTest {

    private TeamMemberService objectToTest;

    private TeamMemberRepository teamMemberRepository = Mockito.mock(TeamMemberRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private TeamService teamService = Mockito.mock(TeamService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamMemberServiceImpl(teamMemberRepository, userInfoService, teamService, modelMapperService);
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
        Long teamMemberIdToFind = 1L;
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

    @Nested
    class createTest {
        private TeamMemberDto.TeamMemberPostRequest teamMemberPostRequestDto = TeamMemberDtoMother.postRequestSample().build();
        private UserInfo userInfoToLink = UserInfoMother.sampleAuthor().build();
        private Team teamToLink = TeamMother.sample().build();
        private TeamMember savedTeamMember = TeamMemberMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(userInfoService.findByUsername(teamMemberPostRequestDto.getUserInfoUsername())).thenReturn(Optional.of(userInfoToLink));
            Mockito.when(teamService.findById(teamMemberPostRequestDto.getTeamId())).thenReturn(Optional.of(teamToLink));
            Mockito.when(teamMemberRepository.save(Mockito.any(TeamMember.class))).thenReturn(savedTeamMember);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(teamMemberPostRequestDto.getUserInfoUsername())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.create(teamMemberPostRequestDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, teamMemberPostRequestDto.getUserInfoUsername()));
        }

        @Test
        void create_noTeamForGivenTeamId_throwNotFoundException() {
            Mockito.when(teamService.findById(teamMemberPostRequestDto.getTeamId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.create(teamMemberPostRequestDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, teamMemberPostRequestDto.getTeamId()));
        }

        @Test
        void create_shouldMapDtoInTeamMember() {
            objectToTest.create(teamMemberPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(TeamMemberDto.class), Mockito.any(TeamMember.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(teamMemberPostRequestDto);

            Mockito.verify(teamMemberRepository).save(Mockito.any(TeamMember.class));
        }

        @Test
        void create_shouldReturnSavedTeamMember() {
            TeamMember result = objectToTest.create(teamMemberPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedTeamMember);
        }

        @Test
        void create_returnedTeamMemberShouldContainLinkedEntities() {
            Mockito.when(teamMemberRepository.save(Mockito.any(TeamMember.class))).then(AdditionalAnswers.returnsFirstArg());

            TeamMember result = objectToTest.create(teamMemberPostRequestDto);

            Assertions.assertThat(result.getTeam()).isEqualTo(teamToLink);
            Assertions.assertThat(result.getUserInfo()).isEqualTo(userInfoToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private TeamMember teamMemberToUpdate = TeamMemberMother.sample().build();
        private TeamMemberDto.TeamMemberPostRequest teamMemberUpdateDto = TeamMemberDtoMother.postRequestSample().build();
        private TeamMember savedTeamMember = TeamMemberMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(teamMemberRepository.findById(givenId)).thenReturn(Optional.ofNullable(teamMemberToUpdate));
            Mockito.when(teamMemberRepository.save(Mockito.any(TeamMember.class))).thenReturn(savedTeamMember);
        }

        @Test
        void update_noTeamMemberForGivenId_throwNotFoundException() {
            Mockito.when(teamMemberRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.update(givenId, teamMemberUpdateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_TEAM_MEMBER_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInTeamMember() {
            objectToTest.update(givenId, teamMemberUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(TeamMemberDto.class), Mockito.any(TeamMember.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, teamMemberUpdateDto);

            Mockito.verify(teamMemberRepository).save(Mockito.any(TeamMember.class));
        }

        @Test
        void update_shouldReturnSavedTeamMember() {
            TeamMember result = objectToTest.update(givenId, teamMemberUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedTeamMember);
        }
    }
}
