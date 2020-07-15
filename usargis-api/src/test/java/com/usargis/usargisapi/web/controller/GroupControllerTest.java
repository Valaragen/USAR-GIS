package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Group;
import com.usargis.usargisapi.service.contract.GroupService;
import com.usargis.usargisapi.service.impl.GroupServiceImpl;
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

class GroupControllerTest {

    private GroupController objectToTest;

    private GroupService groupService = Mockito.mock(GroupServiceImpl.class);

    @BeforeEach
    void setup() {
        objectToTest = new GroupController(groupService);
    }

    @Nested
    class findAllGroupsTest {
        @Test
        void findAllGroups_shouldCallServiceLayer() {
            Mockito.when(groupService.findAll()).thenReturn(Collections.singletonList(new Group()));

            objectToTest.findAllGroups();

            Mockito.verify(groupService).findAll();
        }

        @Test
        void findAllGroups_noGroupFound_throwNotFoundException() {
            Mockito.when(groupService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllGroups())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_GROUPS_FOUND);
        }

        @Test
        void findAllGroups_groupFound_returnResponseEntityWithGroupList() {
            List<Group> returnedGroupList = Collections.singletonList(new Group());
            Mockito.when(groupService.findAll()).thenReturn(returnedGroupList);

            ResponseEntity result = objectToTest.findAllGroups();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedGroupList);
        }
    }

    @Nested
    class getGroupByIdTest {
        private final Long groupIdToFind = 1L;
        private final Group groupFound = new Group();

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
        void getGroupById_groupFound_returnResponseEntityWithGroupAndStatusOk() {
            Mockito.when(groupService.findById(groupIdToFind)).thenReturn(Optional.of(groupFound));

            ResponseEntity result = objectToTest.getGroupById(groupIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(groupFound);
        }
    }

    @Nested
    class createNewGroupTest {
        private final Group groupToSave = new Group();
        private final Group savedGroup = new Group();

        @Test
        void createNewGroup_shouldCallServiceLayer() {
            Mockito.when(groupService.save(groupToSave)).thenReturn(savedGroup);

            objectToTest.createNewGroup(groupToSave);

            Mockito.verify(groupService).save(groupToSave);
        }

        @Test
        void createNewGroup_groupCreated_returnResponseEntityWithGroupAndStatusCreated() {
            Mockito.when(groupService.save(groupToSave)).thenReturn(savedGroup);

            ResponseEntity result = objectToTest.createNewGroup(groupToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedGroup);
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
                    .isEqualTo(null);
        }
    }

}
