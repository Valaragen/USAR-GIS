package com.usargis.usargisapi.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.MissionRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.converter.JsonPatchConverter;
import com.usargis.usargisapi.util.objectMother.dto.MissionDtoMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import com.usargis.usargisapi.web.exception.ProhibitedActionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

class MissionServiceImplTest {

    private MissionService objectToTest;
    private ObjectMapper noMockObjectMapper = new ObjectMapper();

    private MissionRepository missionRepository = Mockito.mock(MissionRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);
    private SecurityService securityService = Mockito.mock(SecurityService.class);
    private ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

    @BeforeEach
    void setup() {
        objectToTest = new MissionServiceImpl(missionRepository, userInfoService, modelMapperService, objectMapper, securityService);
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

    @Nested
    class saveTest {
        Mission missionToSave = MissionMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(missionRepository.save(missionToSave)).thenReturn(missionToSave);
        }

        @Test
        void save_shouldCallRepositoryAndReturnMission() {
            Mission result = objectToTest.save(missionToSave);

            Assertions.assertThat(result).isEqualTo(missionToSave);
            Mockito.verify(missionRepository).save(missionToSave);
        }

        @ParameterizedTest
        @EnumSource(value = MissionStatus.class, names = {"ONGOING", "FINISHED"})
        void save_whenMissionStatusOnCertainStatesAndStartDateIsNull_throwException(MissionStatus missionStatus) {
            missionToSave.setStatus(missionStatus);
            missionToSave.setStartDate(null);

            Assertions.assertThatThrownBy(() -> objectToTest.save(missionToSave))
                    .isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.START_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS, missionStatus.getName()));
        }

        @ParameterizedTest
        @EnumSource(value = MissionStatus.class, names = {"ONGOING", "FINISHED"})
        void save_whenMissionStatusOnCertainStatesAndEndDateIsNull_throwException(MissionStatus missionStatus) {
            missionToSave.setStatus(missionStatus);
            missionToSave.setEndDate(null);

            Assertions.assertThatThrownBy(() -> objectToTest.save(missionToSave))
                    .isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.END_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS, missionStatus.getName()));
        }
    }

    @Test
    void delete_shouldCallRepository() {
        Mission missionToDelete = new Mission();
        Mockito.doNothing().when(missionRepository).delete(missionToDelete);

        objectToTest.delete(missionToDelete);

        Mockito.verify(missionRepository).delete(missionToDelete);
    }

    @Nested
    class createTest {
        private MissionDto.MissionPostRequest missionPostRequestDto = MissionDtoMother.postRequestSample().build();
        private UserInfo authorToLink = UserInfoMother.sampleAuthor().build();
        private String userNameFromToken = authorToLink.getUsername();
        private Mission savedMission = MissionMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(securityService.getUsernameFromToken()).thenReturn(userNameFromToken);
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.of(authorToLink));
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).thenReturn(savedMission);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(userNameFromToken)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.create(missionPostRequestDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, userNameFromToken));
        }

        @Test
        void create_shouldMapDtoInMission() {
            objectToTest.create(missionPostRequestDto);

            Mockito.verify(modelMapperService).map(Mockito.any(MissionDto.class), Mockito.any(Mission.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(missionPostRequestDto);

            Mockito.verify(missionRepository).save(Mockito.any(Mission.class));
        }

        @Test
        void create_shouldReturnSavedMission() {
            Mission result = objectToTest.create(missionPostRequestDto);

            Assertions.assertThat(result).isEqualTo(savedMission);
        }

        @Test
        void create_returnedMissionShouldContainLinkedEntities() {
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).then(AdditionalAnswers.returnsFirstArg());

            Mission result = objectToTest.create(missionPostRequestDto);

            Assertions.assertThat(result.getAuthor()).isEqualTo(authorToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Mission missionToUpdate = MissionMother.sampleFinished().build();
        private MissionDto.MissionPostRequest missionUpdateDto = MissionDtoMother.postRequestSample().build();
        private Mission savedMission = MissionMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.ofNullable(missionToUpdate));
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).thenReturn(savedMission);
        }

        @Test
        void update_noMissionForGivenId_throwNotFoundException() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.update(givenId, missionUpdateDto))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInMission() {
            objectToTest.update(givenId, missionUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(MissionDto.class), Mockito.any(Mission.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, missionUpdateDto);

            Mockito.verify(missionRepository).save(Mockito.any(Mission.class));
        }

        @Test
        void update_shouldReturnSavedMission() {
            Mission result = objectToTest.update(givenId, missionUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedMission);
        }
    }

    @Nested
    class patchTest {
        private Long givenId = 1L;
        private Mission missionToPatch = new Mission();
        private JsonPatch jsonPatch = new JsonPatch(new ArrayList<>());
        private MissionDto.MissionPostRequest missionToPatchAsPostRequest = MissionDto.MissionPostRequest.builder().build();
        private JsonNode missionToPatchAsPostRequestNode = noMockObjectMapper.createObjectNode();

        @BeforeEach
        void setup() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.ofNullable(missionToPatch));
            Mockito.when(modelMapperService.map(missionToPatch, MissionDto.MissionPostRequest.class)).thenReturn(missionToPatchAsPostRequest);
            Mockito.when(objectMapper.valueToTree(missionToPatchAsPostRequest)).thenReturn(missionToPatchAsPostRequestNode);
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).then(AdditionalAnswers.returnsFirstArg());
        }

        @Test
        void patch_noMissionForGivenId_throwNotFoundException() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.patch(givenId, jsonPatch))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, givenId));
        }

        @Test
        void patch_shouldSaveAndReturnPatchedEntity() throws JsonPatchException {
            final ArgumentCaptor<Mission> missionArgumentCaptor = ArgumentCaptor.forClass(Mission.class);

            Mission result = objectToTest.patch(givenId, jsonPatch);

            Mockito.verify(missionRepository).save(missionArgumentCaptor.capture());
            final Mission savedMission = missionArgumentCaptor.getValue();
            Assertions.assertThat(result).isEqualTo(savedMission);
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class patchProcessTest {
        private Long givenId = 1L;
        private Mission missionToPatch = MissionMother.sampleTeamEngagement().build();
        private ModelMapperService modelMapperService = new ModelMapperServiceImpl(new ModelMapper());

        private MissionService objectToTest;

        @BeforeAll
        void setupAll() {
            noMockObjectMapper.registerModule(new JavaTimeModule());
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).then(AdditionalAnswers.returnsFirstArg());
        }

        @BeforeEach
        void setup() {
            missionToPatch = MissionMother.sampleTeamEngagement().build();
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.ofNullable(missionToPatch));
            objectToTest = new MissionServiceImpl(missionRepository, userInfoService, modelMapperService, noMockObjectMapper, securityService);
        }

        @ParameterizedTest
        @MethodSource
        void patch_whenCalledWithValidArguments_shouldPatchCorrectly(
                @ConvertWith(JsonPatchConverter.class) JsonPatch jsonPatchRequest, Mission expected
        ) throws JsonPatchException {
            Mission result = objectToTest.patch(givenId, jsonPatchRequest);

            Assertions.assertThat(result).isEqualTo(expected);
        }

        private Stream<Arguments> patch_whenCalledWithValidArguments_shouldPatchCorrectly() {
            return Stream.of(
                    Arguments.of("[{\"op\": \"replace\", \"path\": \"/name\", \"value\": \"changed\"}]",
                            missionToPatch.toBuilder().name("changed").build()),
                    Arguments.of("[{\"op\": \"replace\", \"path\": \"/startDate\", \"value\": \"2020-10-05T00:00:00.000\"}]",
                            missionToPatch.toBuilder().startDate(LocalDateTime.of(2020, 10, 5, 0, 0)).build()),
                    Arguments.of("[{\"op\": \"add\", \"path\": \"/latitude\", \"value\": \"2.255\"}]",
                            missionToPatch.toBuilder().latitude(2.255).build()),
                    Arguments.of("[{\"op\": \"remove\", \"path\": \"/longitude\"}]",
                            missionToPatch.toBuilder().longitude(null).build())
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "[{\"op\": \"add\", \"path\": \"/creationDate\", \"value\": \"2020-10-05T00:00:00.000\"}]",
                "[{\"op\": \"add\", \"path\": \"/author\", \"value\": {\"name\": \"test\"}}]"
        })
        void patch_whenRequestContainPatchForFieldsThatAreNotInRequestDto_throwIllegalArgumentException(
                @ConvertWith(JsonPatchConverter.class) JsonPatch jsonPatchRequest) {
            Assertions.assertThatThrownBy(() -> {
                objectToTest.patch(givenId, jsonPatchRequest);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
