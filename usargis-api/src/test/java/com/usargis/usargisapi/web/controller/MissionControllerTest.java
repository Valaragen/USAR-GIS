package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.MissionDtoMother;
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

class MissionControllerTest {
    private MissionController objectToTest;

    private MissionService missionService = Mockito.mock(MissionService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new MissionController(missionService, modelMapperService);
    }

    @Nested
    class findAllMissionsTest {
        private final List<Mission> missionsFound = Arrays.asList(new Mission(), new Mission());

        @Test
        void findAllMissions_shouldCallServiceLayer() {
            Mockito.when(missionService.findAll()).thenReturn(missionsFound);

            objectToTest.findAllMissions();

            Mockito.verify(missionService).findAll();
        }

        @Test
        void findAllMissions_noMissionFound_throwNotFoundException() {
            Mockito.when(missionService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllMissions())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_MISSION_FOUND);
        }

        @Test
        void findAllMissions_shouldConvertMissionsToListOfResponseDto() {
            Mockito.when(missionService.findAll()).thenReturn(missionsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Mission.class), Mockito.any())).thenReturn(new MissionDto.MissionResponse());

            objectToTest.findAllMissions();

            Mockito.verify(modelMapperService, Mockito.times(missionsFound.size())).map(Mockito.any(Mission.class), Mockito.any());
        }

        @Test
        void findAllMissions_missionFound_returnStatusOkWithListOfMissionsResponseDto() {
            Mockito.when(missionService.findAll()).thenReturn(missionsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Mission.class), Mockito.any())).thenReturn(new MissionDto.MissionResponse());

            ResponseEntity<List<MissionDto.MissionResponse>> result = objectToTest.findAllMissions();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(missionsFound.size());
        }
    }

    @Nested
    class getMissionByIdTest {
        private final Long missionIdToFind = 1L;
        private final Mission missionFound = new Mission();
        private final MissionDto.MissionResponse missionResponseDto = new MissionDto.MissionResponse();

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
        void getMissionById_shouldConvertMissionToResponseDto() {
            Mockito.when(missionService.findById(missionIdToFind)).thenReturn(Optional.of(missionFound));
            Mockito.when(modelMapperService.map(missionFound, MissionDto.MissionResponse.class)).thenReturn(missionResponseDto);

            objectToTest.getMissionById(missionIdToFind);

            Mockito.verify(modelMapperService).map(missionFound, MissionDto.MissionResponse.class);
        }

        @Test
        void getMissionById_missionFound_returnStatusOkAndMissionResponseDto() {
            Mockito.when(missionService.findById(missionIdToFind)).thenReturn(Optional.of(missionFound));
            Mockito.when(modelMapperService.map(missionFound, MissionDto.MissionResponse.class)).thenReturn(missionResponseDto);

            ResponseEntity<MissionDto.MissionResponse> result = objectToTest.getMissionById(missionIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(missionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(MissionDto.MissionResponse.class);
        }
    }

    @Nested
    class createNewMissionTest {
        private final MissionDto.MissionPostRequest missionToSave = MissionDtoMother.postRequestSample().build();
        private final Mission newMission = new Mission();
        private final MissionDto.MissionResponse missionResponseDto = new MissionDto.MissionResponse();

        @Test
        void createNewMission_shouldCallServiceLayer() {
            Mockito.when(missionService.create(missionToSave)).thenReturn(newMission);

            objectToTest.createNewMission(missionToSave);

            Mockito.verify(missionService).create(missionToSave);
        }

        @Test
        void createNewMission_shouldConvertMissionToResponseDto() {
            Mockito.when(missionService.create(missionToSave)).thenReturn(newMission);
            Mockito.when(modelMapperService.map(newMission, MissionDto.MissionResponse.class)).thenReturn(missionResponseDto);

            objectToTest.createNewMission(missionToSave);

            Mockito.verify(modelMapperService).map(newMission, MissionDto.MissionResponse.class);
        }

        @Test
        void createNewMission_missionCreated_returnStatusCreatedAndMissionResponseDto() {
            Mockito.when(missionService.create(missionToSave)).thenReturn(newMission);
            Mockito.when(modelMapperService.map(newMission, MissionDto.MissionResponse.class)).thenReturn(missionResponseDto);

            ResponseEntity<MissionDto.MissionResponse> result = objectToTest.createNewMission(missionToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(missionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(MissionDto.MissionResponse.class);
        }
    }

    @Nested
    class updateMissionTest {
        private final Long missionId = 1L;
        private final MissionDto.MissionPostRequest missionToUpdate = MissionDtoMother.postRequestSample().build();
        private final Mission updateMission = new Mission();
        private final MissionDto.MissionResponse missionResponseDto = new MissionDto.MissionResponse();

        @Test
        void updateMissionTest_shouldCallServiceLayer() {
            Mockito.when(missionService.update(missionId, missionToUpdate)).thenReturn(updateMission);

            objectToTest.updateMission(missionId, missionToUpdate);

            Mockito.verify(missionService).update(missionId, missionToUpdate);
        }

        @Test
        void updateMissionTest_shouldConvertMissionToResponseDto() {
            Mockito.when(missionService.update(missionId, missionToUpdate))
                    .thenReturn(updateMission);
            Mockito.when(modelMapperService.map(updateMission, MissionDto.MissionResponse.class))
                    .thenReturn(missionResponseDto);

            objectToTest.updateMission(missionId, missionToUpdate);

            Mockito.verify(modelMapperService).map(updateMission, MissionDto.MissionResponse.class);
        }

        @Test
        void updateMissionTest_missionCreated_returnStatusOkAndMissionResponseDto() {
            Mockito.when(missionService.update(missionId, missionToUpdate))
                    .thenReturn(updateMission);
            Mockito.when(modelMapperService.map(updateMission, MissionDto.MissionResponse.class))
                    .thenReturn(missionResponseDto);

            ResponseEntity<MissionDto.MissionResponse> result =
                    objectToTest.updateMission(missionId, missionToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(missionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(MissionDto.MissionResponse.class);
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
                    .isNull();
        }
    }
}
