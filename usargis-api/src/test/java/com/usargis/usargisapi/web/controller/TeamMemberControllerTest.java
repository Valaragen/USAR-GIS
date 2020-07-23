package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamMemberService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.TeamMemberDtoMother;
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

class TeamMemberControllerTest {

    private TeamMemberController objectToTest;

    private TeamMemberService teamMemberService = Mockito.mock(TeamMemberService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new TeamMemberController(teamMemberService, modelMapperService);
    }

    @Nested
    class findAllTeamMembersTest {
        private final List<TeamMember> teamMembersFound = Arrays.asList(new TeamMember(), new TeamMember());

        @Test
        void findAllTeamMembers_shouldCallServiceLayer() {
            Mockito.when(teamMemberService.findAll()).thenReturn(teamMembersFound);

            objectToTest.findAllTeamMembers();

            Mockito.verify(teamMemberService).findAll();
        }

        @Test
        void findAllTeamMembers_noTeamMemberFound_throwNotFoundException() {
            Mockito.when(teamMemberService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllTeamMembers())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_TEAM_MEMBER_FOUND);
        }

        @Test
        void findAllTeamMembers_shouldConvertTeamMembersToListOfResponseDto() {
            Mockito.when(teamMemberService.findAll()).thenReturn(teamMembersFound);
            Mockito.when(modelMapperService.map(Mockito.any(TeamMember.class), Mockito.any())).thenReturn(new TeamMemberDto.TeamMemberResponse());

            objectToTest.findAllTeamMembers();

            Mockito.verify(modelMapperService, Mockito.times(teamMembersFound.size())).map(Mockito.any(TeamMember.class), Mockito.any());
        }

        @Test
        void findAllTeamMembers_teamMemberFound_returnStatusOkWithListOfTeamMembersResponseDto() {
            Mockito.when(teamMemberService.findAll()).thenReturn(teamMembersFound);
            Mockito.when(modelMapperService.map(Mockito.any(TeamMember.class), Mockito.any())).thenReturn(new TeamMemberDto.TeamMemberResponse());

            ResponseEntity<List<TeamMemberDto.TeamMemberResponse>> result = objectToTest.findAllTeamMembers();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(teamMembersFound.size());
        }
    }

    @Nested
    class getTeamMemberByIdTest {
        private final Long teamMemberIdToFind = 1L;
        private final TeamMember teamMemberFound = new TeamMember();
        private final TeamMemberDto.TeamMemberResponse teamMemberResponseDto = new TeamMemberDto.TeamMemberResponse();

        @Test
        void getTeamMemberById_shouldCallServiceLayer() {
            Mockito.when(teamMemberService.findById(teamMemberIdToFind)).thenReturn(Optional.of(teamMemberFound));

            objectToTest.getTeamMemberById(teamMemberIdToFind);

            Mockito.verify(teamMemberService).findById(teamMemberIdToFind);
        }

        @Test
        void getTeamMemberById_noTeamMemberFound_throwNotFoundException() {
            Mockito.when(teamMemberService.findById(teamMemberIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getTeamMemberById(teamMemberIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_TEAM_MEMBER_FOUND_FOR_ID, teamMemberIdToFind));
        }

        @Test
        void getTeamMemberById_shouldConvertTeamMemberToResponseDto() {
            Mockito.when(teamMemberService.findById(teamMemberIdToFind)).thenReturn(Optional.of(teamMemberFound));
            Mockito.when(modelMapperService.map(teamMemberFound, TeamMemberDto.TeamMemberResponse.class)).thenReturn(teamMemberResponseDto);

            objectToTest.getTeamMemberById(teamMemberIdToFind);

            Mockito.verify(modelMapperService).map(teamMemberFound, TeamMemberDto.TeamMemberResponse.class);
        }

        @Test
        void getTeamMemberById_teamMemberFound_returnStatusOkAndTeamMemberResponseDto() {
            Mockito.when(teamMemberService.findById(teamMemberIdToFind)).thenReturn(Optional.of(teamMemberFound));
            Mockito.when(modelMapperService.map(teamMemberFound, TeamMemberDto.TeamMemberResponse.class)).thenReturn(teamMemberResponseDto);

            ResponseEntity<TeamMemberDto.TeamMemberResponse> result = objectToTest.getTeamMemberById(teamMemberIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamMemberResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamMemberDto.TeamMemberResponse.class);
        }
    }

    @Nested
    class createNewTeamMemberTest {
        private final TeamMemberDto.TeamMemberPostRequest teamMemberToSave = TeamMemberDtoMother.postRequestSample().build();
        private final TeamMember newTeamMember = new TeamMember();
        private final TeamMemberDto.TeamMemberResponse teamMemberResponseDto = new TeamMemberDto.TeamMemberResponse();

        @Test
        void createNewTeamMember_shouldCallServiceLayer() {
            Mockito.when(teamMemberService.create(teamMemberToSave)).thenReturn(newTeamMember);

            objectToTest.createNewTeamMember(teamMemberToSave);

            Mockito.verify(teamMemberService).create(teamMemberToSave);
        }

        @Test
        void createNewTeamMember_shouldConvertTeamMemberToResponseDto() {
            Mockito.when(teamMemberService.create(teamMemberToSave)).thenReturn(newTeamMember);
            Mockito.when(modelMapperService.map(newTeamMember, TeamMemberDto.TeamMemberResponse.class)).thenReturn(teamMemberResponseDto);

            objectToTest.createNewTeamMember(teamMemberToSave);

            Mockito.verify(modelMapperService).map(newTeamMember, TeamMemberDto.TeamMemberResponse.class);
        }

        @Test
        void createNewTeamMember_teamMemberCreated_returnStatusCreatedAndTeamMemberResponseDto() {
            Mockito.when(teamMemberService.create(teamMemberToSave)).thenReturn(newTeamMember);
            Mockito.when(modelMapperService.map(newTeamMember, TeamMemberDto.TeamMemberResponse.class)).thenReturn(teamMemberResponseDto);

            ResponseEntity<TeamMemberDto.TeamMemberResponse> result = objectToTest.createNewTeamMember(teamMemberToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamMemberResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamMemberDto.TeamMemberResponse.class);
        }
    }

    @Nested
    class updateTeamMemberTest {
        private final Long teamMemberId = 1L;
        private final TeamMemberDto.TeamMemberPostRequest teamMemberToUpdate = TeamMemberDtoMother.postRequestSample().build();
        private final TeamMember updateTeamMember = new TeamMember();
        private final TeamMemberDto.TeamMemberResponse teamMemberResponseDto = new TeamMemberDto.TeamMemberResponse();

        @Test
        void updateTeamMemberTest_shouldCallServiceLayer() {
            Mockito.when(teamMemberService.update(teamMemberId, teamMemberToUpdate)).thenReturn(updateTeamMember);

            objectToTest.updateTeamMember(teamMemberId, teamMemberToUpdate);

            Mockito.verify(teamMemberService).update(teamMemberId, teamMemberToUpdate);
        }

        @Test
        void updateTeamMemberTest_shouldConvertTeamMemberToResponseDto() {
            Mockito.when(teamMemberService.update(teamMemberId, teamMemberToUpdate))
                    .thenReturn(updateTeamMember);
            Mockito.when(modelMapperService.map(updateTeamMember, TeamMemberDto.TeamMemberResponse.class))
                    .thenReturn(teamMemberResponseDto);

            objectToTest.updateTeamMember(teamMemberId, teamMemberToUpdate);

            Mockito.verify(modelMapperService).map(updateTeamMember, TeamMemberDto.TeamMemberResponse.class);
        }

        @Test
        void updateTeamMemberTest_teamMemberCreated_returnStatusOkAndTeamMemberResponseDto() {
            Mockito.when(teamMemberService.update(teamMemberId, teamMemberToUpdate))
                    .thenReturn(updateTeamMember);
            Mockito.when(modelMapperService.map(updateTeamMember, TeamMemberDto.TeamMemberResponse.class))
                    .thenReturn(teamMemberResponseDto);

            ResponseEntity<TeamMemberDto.TeamMemberResponse> result =
                    objectToTest.updateTeamMember(teamMemberId, teamMemberToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(teamMemberResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(TeamMemberDto.TeamMemberResponse.class);
        }
    }

    @Nested
    class deleteTeamMemberTest {
        private final Long teamMemberToDeleteId = 1L;
        private final TeamMember foundTeamMemberToDelete = new TeamMember();

        @Test
        void deleteTeamMember_shouldCallServiceLayer() {
            Mockito.when(teamMemberService.findById(teamMemberToDeleteId)).thenReturn(Optional.of(foundTeamMemberToDelete));
            Mockito.doNothing().when(teamMemberService).delete(foundTeamMemberToDelete);

            objectToTest.deleteTeamMember(teamMemberToDeleteId);

            Mockito.verify(teamMemberService).findById(teamMemberToDeleteId);
            Mockito.verify(teamMemberService).delete(foundTeamMemberToDelete);
        }

        @Test
        void deleteTeamMember_teamMemberDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(teamMemberService.findById(teamMemberToDeleteId)).thenReturn(Optional.of(foundTeamMemberToDelete));
            Mockito.doNothing().when(teamMemberService).delete(foundTeamMemberToDelete);

            ResponseEntity result = objectToTest.deleteTeamMember(teamMemberToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
