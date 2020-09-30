package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.repository.GroupRepository;
import com.usargis.usargisapi.service.contract.GroupService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.GroupDtoMother;
import com.usargis.usargisapi.util.objectMother.model.GroupMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class GroupServiceImplTest {

    private GroupService objectToTest;

    private GroupRepository groupRepository = Mockito.mock(GroupRepository.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new GroupServiceImpl(groupRepository, modelMapperService);
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

    @Nested
    class createTest {
        private GroupDto.GroupPostRequest groupPostRequestDto = GroupDtoMother.postRequestSample().build();
        private Group savedGroup = GroupMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(groupRepository.save(Mockito.any(Group.class))).thenReturn(savedGroup);
        }

        @Test
        void create_shouldMapDtoInGroup() {
            objectToTest.create(groupPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(GroupDto.class), Mockito.any(Group.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(groupPostRequestDto);

            Mockito.verify(groupRepository).save(Mockito.any(Group.class));
        }

        @Test
        void create_shouldReturnSavedGroup() {
            Group result = objectToTest.create(groupPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedGroup);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Group groupToUpdate = GroupMother.sample().build();
        private GroupDto.GroupPostRequest groupUpdateDto = GroupDtoMother.postRequestSample().build();
        private Group savedGroup = GroupMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(groupRepository.findById(givenId)).thenReturn(Optional.ofNullable(groupToUpdate));
            Mockito.when(groupRepository.save(Mockito.any(Group.class))).thenReturn(savedGroup);
        }

        @Test
        void update_noGroupForGivenId_throwNotFoundException() {
            Mockito.when(groupRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.update(givenId, groupUpdateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInGroup() {
            objectToTest.update(givenId, groupUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(GroupDto.class), Mockito.any(Group.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, groupUpdateDto);

            Mockito.verify(groupRepository).save(Mockito.any(Group.class));
        }

        @Test
        void update_shouldReturnSavedGroup() {
            Group result = objectToTest.update(givenId, groupUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedGroup);
        }
    }

}
