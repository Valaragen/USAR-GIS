package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.Mission;
import com.usargis.usargisapi.repository.MissionRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class MissionServiceImplTest {

    private MissionService objectToTest;

    private MissionRepository missionRepository = Mockito.mock(MissionRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new MissionServiceImpl(missionRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Mission> returnedMissionList = Collections.singletonList(new Mission());
        Mockito.when(missionRepository.findAll()).thenReturn(returnedMissionList);

        List<Mission> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedMissionList);
        Mockito.verify(missionRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Mission missionFound = new Mission();
        Optional<Mission> expectedResult = Optional.of(missionFound);
        Long missionIdToFind = 1L;
        Mockito.when(missionRepository.findById(missionIdToFind)).thenReturn(expectedResult);

        Optional<Mission> result = objectToTest.findById(missionIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(missionRepository).findById(missionIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnMission() {
        Mission missionToSave = new Mission();
        Mockito.when(missionRepository.save(missionToSave)).thenReturn(missionToSave);

        Mission result = objectToTest.save(missionToSave);

        Assertions.assertThat(result).isEqualTo(missionToSave);
        Mockito.verify(missionRepository).save(missionToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Mission missionToDelete = new Mission();
        Mockito.doNothing().when(missionRepository).delete(missionToDelete);

        objectToTest.delete(missionToDelete);

        Mockito.verify(missionRepository).delete(missionToDelete);
    }

}
