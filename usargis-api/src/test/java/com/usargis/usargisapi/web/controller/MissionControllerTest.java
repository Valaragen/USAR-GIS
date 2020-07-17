package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Mission;
import com.usargis.usargisapi.service.contract.MissionService;
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

class MissionControllerTest {

    private MissionController objectToTest;

    private MissionService missionService = Mockito.mock(MissionService.class);

    @BeforeEach
    void setup() {
        objectToTest = new MissionController(missionService);
    }

    @Nested
    class findAllMissionsTest {
        @Test
        void findAllMissions_shouldCallServiceLayer() {
            Mockito.when(missionService.findAll()).thenReturn(Collections.singletonList(new Mission()));

            objectToTest.findAllMissions();

            Mockito.verify(missionService).findAll();
        }

        @Test
        void findAllMissions_noMissionFound_throwNotFoundException() {
            Mockito.when(missionService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllMissions())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_MISSIONS_FOUND);
        }

        @Test
        void findAllMissions_missionFound_returnResponseEntityWithMissionList() {
            List<Mission> returnedMissionList = Collections.singletonList(new Mission());
            Mockito.when(missionService.findAll()).thenReturn(returnedMissionList);

            ResponseEntity result = objectToTest.findAllMissions();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedMissionList);
        }
    }

    @Nested
    class getMissionByIdTest {
        private final Long missionIdToFind = 1L;
        private final Mission missionFound = new Mission();

        @Test
        void getMissionById_shouldCallServiceLayer() {
            Mockito.when(missionService.findById(missionIdToFind)).thenReturn(Optional.of(missionFound));

            objectToTest.getMissionById(missionIdToFind);

            Mockito.verify(missionService).findById(missionIdToFind);
        }

        @Test
        void getMissionById_noMissionFound_throwNotFoundException() {
            Mockito.when(missionService.findById(missionIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getMissionById(missionIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, missionIdToFind));
        }

        @Test
        void getMissionById_missionFound_returnResponseEntityWithMissionAndStatusOk() {
            Mockito.when(missionService.findById(missionIdToFind)).thenReturn(Optional.of(missionFound));

            ResponseEntity result = objectToTest.getMissionById(missionIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(missionFound);
        }
    }

    @Nested
    class createNewMissionTest {
        private final Mission missionToSave = new Mission();
        private final Mission savedMission = new Mission();

        @Test
        void createNewMission_shouldCallServiceLayer() {
            Mockito.when(missionService.save(missionToSave)).thenReturn(savedMission);

            objectToTest.createNewMission(missionToSave);

            Mockito.verify(missionService).save(missionToSave);
        }

        @Test
        void createNewMission_missionCreated_returnResponseEntityWithMissionAndStatusCreated() {
            Mockito.when(missionService.save(missionToSave)).thenReturn(savedMission);

            ResponseEntity result = objectToTest.createNewMission(missionToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedMission);
        }
    }

    @Nested
    class deleteMissionTest {
        private final Long missionToDeleteId = 1L;
        private final Mission foundMissionToDelete = new Mission();

        @Test
        void deleteMission_shouldCallServiceLayer() {
            Mockito.when(missionService.findById(missionToDeleteId)).thenReturn(Optional.of(foundMissionToDelete));
            Mockito.doNothing().when(missionService).delete(foundMissionToDelete);

            objectToTest.deleteMission(missionToDeleteId);

            Mockito.verify(missionService).findById(missionToDeleteId);
            Mockito.verify(missionService).delete(foundMissionToDelete);
        }

        @Test
        void deleteMission_missionDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(missionService.findById(missionToDeleteId)).thenReturn(Optional.of(foundMissionToDelete));
            Mockito.doNothing().when(missionService).delete(foundMissionToDelete);

            ResponseEntity result = objectToTest.deleteMission(missionToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(null);
        }
    }

}
