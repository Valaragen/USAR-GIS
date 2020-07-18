package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.repository.GroupRepository;
import com.usargis.usargisapi.service.contract.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class GroupServiceImplTest {

    private GroupService objectToTest;

    private GroupRepository groupRepository = Mockito.mock(GroupRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new GroupServiceImpl(groupRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Group> returnedGroupList = Collections.singletonList(new Group());
        Mockito.when(groupRepository.findAll()).thenReturn(returnedGroupList);

        List<Group> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedGroupList);
        Mockito.verify(groupRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Group groupFound = new Group();
        Optional<Group> expectedResult = Optional.of(groupFound);
        Long groupIdToFind = 1L;
        Mockito.when(groupRepository.findById(groupIdToFind)).thenReturn(expectedResult);

        Optional<Group> result = objectToTest.findById(groupIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(groupRepository).findById(groupIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnGroup() {
        Group groupToSave = new Group();
        Mockito.when(groupRepository.save(groupToSave)).thenReturn(groupToSave);

        Group result = objectToTest.save(groupToSave);

        Assertions.assertThat(result).isEqualTo(groupToSave);
        Mockito.verify(groupRepository).save(groupToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Group groupToDelete = new Group();
        Mockito.doNothing().when(groupRepository).delete(groupToDelete);

        objectToTest.delete(groupToDelete);

        Mockito.verify(groupRepository).delete(groupToDelete);
    }

}
