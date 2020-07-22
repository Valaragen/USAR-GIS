package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.MissionRepository;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.MissionDtoMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
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

class MissionServiceImplTest {

    private MissionService objectToTest;

    private MissionRepository missionRepository = Mockito.mock(MissionRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);
    private SecurityService securityService = Mockito.mock(SecurityService.class);

    @BeforeEach
    void setup() {
        objectToTest = new MissionServiceImpl(missionRepository, userInfoService, modelMapperService, securityService);
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

    @Nested
    class createTest {
        private MissionDto.PostRequest missionPostRequestDto = MissionDtoMother.postRequestSample().build();
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

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(missionPostRequestDto);
            }).isInstanceOf(NotFoundException.class)
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
        void create_returnedAbilityShouldContainLinkedEntities() {
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).then(AdditionalAnswers.returnsFirstArg());

            Mission result = objectToTest.create(missionPostRequestDto);

            Assertions.assertThat(result.getAuthor()).isEqualTo(authorToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Mission missionToUpdate = MissionMother.sampleFinished().build();
        private MissionDto.PostRequest missionUpdateDto = MissionDtoMother.postRequestSample().build();
        private Mission savedMission = MissionMother.sampleFinished().build();

        @BeforeEach
        void setup() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.ofNullable(missionToUpdate));
            Mockito.when(missionRepository.save(Mockito.any(Mission.class))).thenReturn(savedMission);
        }

        @Test
        void update_noMissionForGivenId_throwNotFoundException() {
            Mockito.when(missionRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.update(givenId, missionUpdateDto);
            }).isInstanceOf(NotFoundException.class)
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
}
