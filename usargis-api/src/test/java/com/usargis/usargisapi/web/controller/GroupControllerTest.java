package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.service.contract.GroupService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.GroupDtoMother;
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

class GroupControllerTest {
    private GroupController objectToTest;

    private GroupService groupService = Mockito.mock(GroupService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new GroupController(groupService, modelMapperService);
    }

    @Nested
    class findAllGroupsTest {
        private final List<Group> groupsFound = Arrays.asList(new Group(), new Group());

        @Test
        void findAllGroups_shouldCallServiceLayer() {
            Mockito.when(groupService.findAll()).thenReturn(groupsFound);

            objectToTest.findAllGroups();

            Mockito.verify(groupService).findAll();
        }

        @Test
        void findAllGroups_noGroupFound_throwNotFoundException() {
            Mockito.when(groupService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllGroups())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_GROUP_FOUND);
        }

        @Test
        void findAllGroups_shouldConvertGroupsToListOfResponseDto() {
            Mockito.when(groupService.findAll()).thenReturn(groupsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Group.class), Mockito.any())).thenReturn(new GroupDto.GroupResponse());

            objectToTest.findAllGroups();

            Mockito.verify(modelMapperService, Mockito.times(groupsFound.size())).map(Mockito.any(Group.class), Mockito.any());
        }

        @Test
        void findAllGroups_groupFound_returnStatusOkWithListOfGroupsResponseDto() {
            Mockito.when(groupService.findAll()).thenReturn(groupsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Group.class), Mockito.any())).thenReturn(new GroupDto.GroupResponse());

            ResponseEntity<List<GroupDto.GroupResponse>> result = objectToTest.findAllGroups();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(groupsFound.size());
        }
    }

    @Nested
    class getGroupByIdTest {
        private final Long groupIdToFind = 1L;
        private final Group groupFound = new Group();
        private final GroupDto.GroupResponse groupResponseDto = new GroupDto.GroupResponse();

        @Test
        void getGroupById_shouldCallServiceLayer() {
            Mockito.when(groupService.findById(groupIdToFind)).thenReturn(Optional.of(groupFound));

            objectToTest.getGroupById(groupIdToFind);

            Mockito.verify(groupService).findById(groupIdToFind);
        }

        @Test
        void getGroupById_noGroupFound_throwNotFoundException() {
            Mockito.when(groupService.findById(groupIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getGroupById(groupIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, groupIdToFind));
        }

        @Test
        void getGroupById_shouldConvertGroupToResponseDto() {
            Mockito.when(groupService.findById(groupIdToFind)).thenReturn(Optional.of(groupFound));
            Mockito.when(modelMapperService.map(groupFound, GroupDto.GroupResponse.class)).thenReturn(groupResponseDto);

            objectToTest.getGroupById(groupIdToFind);

            Mockito.verify(modelMapperService).map(groupFound, GroupDto.GroupResponse.class);
        }

        @Test
        void getGroupById_groupFound_returnStatusOkAndGroupResponseDto() {
            Mockito.when(groupService.findById(groupIdToFind)).thenReturn(Optional.of(groupFound));
            Mockito.when(modelMapperService.map(groupFound, GroupDto.GroupResponse.class)).thenReturn(groupResponseDto);

            ResponseEntity<GroupDto.GroupResponse> result = objectToTest.getGroupById(groupIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(groupResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(GroupDto.GroupResponse.class);
        }
    }

    @Nested
    class createNewGroupTest {
        private final GroupDto.GroupPostRequest groupToSave = GroupDtoMother.postRequestSample().build();
        private final Group newGroup = new Group();
        private final GroupDto.GroupResponse groupResponseDto = new GroupDto.GroupResponse();

        @Test
        void createNewGroup_shouldCallServiceLayer() {
            Mockito.when(groupService.create(groupToSave)).thenReturn(newGroup);

            objectToTest.createNewGroup(groupToSave);

            Mockito.verify(groupService).create(groupToSave);
        }

        @Test
        void createNewGroup_shouldConvertGroupToResponseDto() {
            Mockito.when(groupService.create(groupToSave)).thenReturn(newGroup);
            Mockito.when(modelMapperService.map(newGroup, GroupDto.GroupResponse.class)).thenReturn(groupResponseDto);

            objectToTest.createNewGroup(groupToSave);

            Mockito.verify(modelMapperService).map(newGroup, GroupDto.GroupResponse.class);
        }

        @Test
        void createNewGroup_groupCreated_returnStatusCreatedAndGroupResponseDto() {
            Mockito.when(groupService.create(groupToSave)).thenReturn(newGroup);
            Mockito.when(modelMapperService.map(newGroup, GroupDto.GroupResponse.class)).thenReturn(groupResponseDto);

            ResponseEntity<GroupDto.GroupResponse> result = objectToTest.createNewGroup(groupToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(groupResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(GroupDto.GroupResponse.class);
        }
    }

    @Nested
    class updateGroupTest {
        private final Long groupId = 1L;
        private final GroupDto.GroupPostRequest groupToUpdate = GroupDtoMother.postRequestSample().build();
        private final Group updateGroup = new Group();
        private final GroupDto.GroupResponse groupResponseDto = new GroupDto.GroupResponse();

        @Test
        void updateGroupTest_shouldCallServiceLayer() {
            Mockito.when(groupService.update(groupId, groupToUpdate)).thenReturn(updateGroup);

            objectToTest.updateGroup(groupId, groupToUpdate);

            Mockito.verify(groupService).update(groupId, groupToUpdate);
        }

        @Test
        void updateGroupTest_shouldConvertGroupToResponseDto() {
            Mockito.when(groupService.update(groupId, groupToUpdate))
                    .thenReturn(updateGroup);
            Mockito.when(modelMapperService.map(updateGroup, GroupDto.GroupResponse.class))
                    .thenReturn(groupResponseDto);

            objectToTest.updateGroup(groupId, groupToUpdate);

            Mockito.verify(modelMapperService).map(updateGroup, GroupDto.GroupResponse.class);
        }

        @Test
        void updateGroupTest_groupCreated_returnStatusOkAndGroupResponseDto() {
            Mockito.when(groupService.update(groupId, groupToUpdate))
                    .thenReturn(updateGroup);
            Mockito.when(modelMapperService.map(updateGroup, GroupDto.GroupResponse.class))
                    .thenReturn(groupResponseDto);

            ResponseEntity<GroupDto.GroupResponse> result =
                    objectToTest.updateGroup(groupId, groupToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(groupResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(GroupDto.GroupResponse.class);
        }
    }

    @Nested
    class deleteGroupTest {
        private final Long groupToDeleteId = 1L;
        private final Group foundGroupToDelete = new Group();

        @Test
        void deleteGroup_shouldCallServiceLayer() {
            Mockito.when(groupService.findById(groupToDeleteId)).thenReturn(Optional.of(foundGroupToDelete));
            Mockito.doNothing().when(groupService).delete(foundGroupToDelete);

            objectToTest.deleteGroup(groupToDeleteId);

            Mockito.verify(groupService).findById(groupToDeleteId);
            Mockito.verify(groupService).delete(foundGroupToDelete);
        }

        @Test
        void deleteGroup_groupDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(groupService.findById(groupToDeleteId)).thenReturn(Optional.of(foundGroupToDelete));
            Mockito.doNothing().when(groupService).delete(foundGroupToDelete);

            ResponseEntity result = objectToTest.deleteGroup(groupToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
