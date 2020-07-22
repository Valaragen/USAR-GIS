package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.AvailabilityDtoMother;
import com.usargis.usargisapi.util.objectMother.model.AvailabilityMother;
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

class AvailabilityServiceImplTest {

    private AvailabilityService objectToTest;

    private AvailabilityRepository availabilityRepository = Mockito.mock(AvailabilityRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private MissionService missionService = Mockito.mock(MissionService.class);

    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new AvailabilityServiceImpl(availabilityRepository, userInfoService, missionService, modelMapperService);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Availability> returnedAvailabilityList = Collections.singletonList(new Availability());
        Mockito.when(availabilityRepository.findAll()).thenReturn(returnedAvailabilityList);

        List<Availability> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedAvailabilityList);
        Mockito.verify(availabilityRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Availability availabilityFound = new Availability();
        Optional<Availability> expectedResult = Optional.of(availabilityFound);
        Long availabilityIdToFind = 1L;
        Mockito.when(availabilityRepository.findById(availabilityIdToFind)).thenReturn(expectedResult);

        Optional<Availability> result = objectToTest.findById(availabilityIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(availabilityRepository).findById(availabilityIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnAvailability() {
        Availability availabilityToSave = new Availability();
        Mockito.when(availabilityRepository.save(availabilityToSave)).thenReturn(availabilityToSave);

        Availability result = objectToTest.save(availabilityToSave);

        Assertions.assertThat(result).isEqualTo(availabilityToSave);
        Mockito.verify(availabilityRepository).save(availabilityToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Availability availabilityToDelete = new Availability();
        Mockito.doNothing().when(availabilityRepository).delete(availabilityToDelete);

        objectToTest.delete(availabilityToDelete);

        Mockito.verify(availabilityRepository).delete(availabilityToDelete);
    }

    @Test
    void searchAll_shouldCallRepositoryAndReturnOptional() {
        List<Availability> returnedAvailabilityList = Collections.singletonList(new Availability());
        AvailabilitySearch availabilitySearch = new AvailabilitySearch();
        Mockito.when(availabilityRepository.searchAll(availabilitySearch)).thenReturn(returnedAvailabilityList);

        List<Availability> result = objectToTest.searchAll(availabilitySearch);

        Assertions.assertThat(result).isEqualTo(returnedAvailabilityList);
        Mockito.verify(availabilityRepository).searchAll(availabilitySearch);
    }


    @Nested
    class createTest {
        private AvailabilityDto.Create availabilityCreateDto = AvailabilityDtoMother.createSample().build();
        private UserInfo userToLink = UserInfoMother.sample().build();
        private Mission missionToLink = MissionMother.sampleFinished().build();
        private Availability savedAvailability = AvailabilityMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(userInfoService.findByUsername(availabilityCreateDto.getUserInfoUsername())).thenReturn(Optional.of(userToLink));
            Mockito.when(missionService.findById(availabilityCreateDto.getMissionId())).thenReturn(Optional.of(missionToLink));
            Mockito.when(availabilityRepository.save(Mockito.any(Availability.class))).thenReturn(savedAvailability);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(availabilityCreateDto.getUserInfoUsername())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(availabilityCreateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, availabilityCreateDto.getUserInfoUsername()));
        }

        @Test
        void create_noMissionForGivenId_throwNotFoundException() {
            Mockito.when(missionService.findById(availabilityCreateDto.getMissionId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(availabilityCreateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, availabilityCreateDto.getMissionId()));
        }

        @Test
        void create_shouldMapDtoInAvailability() {
            objectToTest.create(availabilityCreateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(AvailabilityDto.class), Mockito.any(Availability.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(availabilityCreateDto);

            Mockito.verify(availabilityRepository).save(Mockito.any(Availability.class));
        }

        @Test
        void create_shouldReturnSavedAvailability() {
            Availability result = objectToTest.create(availabilityCreateDto);

            Assertions.assertThat(result).isEqualTo(savedAvailability);
        }

        @Test
        void create_returnedAbilityShouldContainLinkedEntities() {
            Mockito.when(availabilityRepository.save(Mockito.any(Availability.class))).then(AdditionalAnswers.returnsFirstArg());

            Availability result = objectToTest.create(availabilityCreateDto);

            Assertions.assertThat(result.getUserInfo()).isEqualTo(userToLink);
            Assertions.assertThat(result.getMission()).isEqualTo(missionToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Availability availabilityToUpdate = AvailabilityMother.sample().build();
        private AvailabilityDto.Update availabilityUpdateDto = AvailabilityDtoMother.updateSample().build();
        private Availability savedAvailability = AvailabilityMother.sample().build();

        @BeforeEach
        void setup() {
            Mockito.when(availabilityRepository.findById(givenId)).thenReturn(Optional.ofNullable(availabilityToUpdate));
            Mockito.when(availabilityRepository.save(Mockito.any(Availability.class))).thenReturn(savedAvailability);
        }

        @Test
        void update_noAvailabilityForGivenId_throwNotFoundException() {
            Mockito.when(availabilityRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.update(givenId, availabilityUpdateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInAvailability() {
            objectToTest.update(givenId, availabilityUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(AvailabilityDto.class), Mockito.any(Availability.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, availabilityUpdateDto);

            Mockito.verify(availabilityRepository).save(Mockito.any(Availability.class));
        }

        @Test
        void update_shouldReturnSavedAvailability() {
            Availability result = objectToTest.update(givenId, availabilityUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedAvailability);
        }
    }
}
