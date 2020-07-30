package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.*;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.AvailabilityDtoMother;
import com.usargis.usargisapi.util.objectMother.model.AvailabilityMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import com.usargis.usargisapi.web.exception.ProhibitedActionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

class AvailabilityServiceImplTest {

    private AvailabilityService objectToTest;

    private AvailabilityRepository availabilityRepository = Mockito.mock(AvailabilityRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private MissionService missionService = Mockito.mock(MissionService.class);
    private SecurityService securityService = Mockito.mock(SecurityService.class);

    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new AvailabilityServiceImpl(availabilityRepository, userInfoService, missionService,
                securityService, modelMapperService);
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

    @Nested
    class saveTest {
        private Availability availabilityToSave = AvailabilityMother.sample().build();
        private List<Availability> availabilitiesSavedBySameUserOnSameMission = new ArrayList<>();

        @BeforeEach
        void setup() {
            Mockito.when(availabilityRepository.searchAll(Mockito.any(AvailabilitySearch.class))).thenReturn(availabilitiesSavedBySameUserOnSameMission);
            Mockito.when(availabilityRepository.save(availabilityToSave)).thenReturn(availabilityToSave);
        }

        @Test
        void save_shouldCallRepositoryAndReturnAvailability() {
            Availability result = objectToTest.save(availabilityToSave);

            Assertions.assertThat(result).isEqualTo(availabilityToSave);
            Mockito.verify(availabilityRepository).save(availabilityToSave);
        }

        @ParameterizedTest
        @EnumSource(value = MissionStatus.class, names = {"ONFOCUS", "ONGOING", "FINISHED", "CANCELLED"})
        void save_whenMissionStatusOnCertainStatesAndEndDateIsNull_throwException(MissionStatus missionStatus) {
            availabilityToSave.getMission().setStatus(missionStatus);

            Assertions.assertThatThrownBy(() -> {
                objectToTest.save(availabilityToSave);
            }).isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.AVAILABILITY_CANT_BE_CREATED_OR_UPDATED_WHEN_LINKED_MISSION_STATUS_IS, missionStatus.getName()));
        }

        @Test
        void save_whenStartDateIsAfterEndDate() {
            availabilityToSave.setStartDate(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
            availabilityToSave.setEndDate(LocalDateTime.now());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.save(availabilityToSave);
            }).isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(ErrorConstant.AVAILABILITY_START_DATE_CANT_BE_AFTER_END_DATE);
        }

        @Test
        void save_whenStartDateIsEqualToEndDate_throwException() {
            LocalDateTime sameDate = LocalDateTime.now();
            availabilityToSave.setStartDate(sameDate);
            availabilityToSave.setEndDate(sameDate);

            Assertions.assertThatThrownBy(() -> {
                objectToTest.save(availabilityToSave);
            }).isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(ErrorConstant.AVAILABILITY_START_DATE_CANT_BE_EQUAL_TO_END_DATE);
        }

        @ParameterizedTest
        @CsvSource({
                "2020-01-05T00:00:00, 2020-01-08T00:00:00",
                "2020-01-05T00:00:00, 2020-01-07T00:00:00",
                "2020-01-05T00:00:00, 2020-01-06T12:00:00",
                "2020-01-06T01:00:00, 2020-01-06T12:00:00",
                "2020-01-06T12:00:00, 2020-01-08T00:00:00",
                "2020-01-06T00:00:00, 2020-01-08T00:00:00"
        })
        void save_whenAvailabilityDatesOverlapAnotherAlreadyCreatedAvailability_throwException(LocalDateTime startDate, LocalDateTime endDate) {
            Availability sampleAvailability = AvailabilityMother.sample().build();
            sampleAvailability.setId(10L);
            availabilitiesSavedBySameUserOnSameMission.add(sampleAvailability);
            Availability availabilityAlreadySaved = AvailabilityMother.sample()
                    .startDate(LocalDateTime.of(2020,1,6,0,0,0))
                    .endDate(LocalDateTime.of(2020,1,7,0,0,0)).build();
            availabilityAlreadySaved.setId(2L);
            availabilitiesSavedBySameUserOnSameMission.add(availabilityAlreadySaved);
            availabilityToSave.setStartDate(startDate);
            availabilityToSave.setEndDate(endDate);

            Assertions.assertThatThrownBy(() -> {
                objectToTest.save(availabilityToSave);
            }).isInstanceOf(ProhibitedActionException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.AVAILABILITY_ALREADY_COVERED_BY_THE_AVAILABILITY_OF_ID_WITH_START_DATE_AND_END_DATE,
                            availabilityAlreadySaved.getId(), availabilityAlreadySaved.getStartDate(), availabilityAlreadySaved.getEndDate()));
        }

        @Test
        void save_whenAvailabilityWithSameIdOverlapAnotherAlreadyCreatedAvailability_shouldNotThrowException() {
            Long availabilityToSaveId = 2L;
            Availability sampleAvailability = AvailabilityMother.sample().build();
            sampleAvailability.setId(10L);
            availabilitiesSavedBySameUserOnSameMission.add(sampleAvailability);
            Availability availabilityAlreadySaved = AvailabilityMother.sample()
                    .startDate(LocalDateTime.of(2020,1,6,0,0,0))
                    .endDate(LocalDateTime.of(2020,1,7,0,0,0)).build();
            availabilityAlreadySaved.setId(availabilityToSaveId);
            availabilitiesSavedBySameUserOnSameMission.add(availabilityAlreadySaved);
            availabilityToSave.setId(availabilityToSaveId);
            availabilityToSave.setStartDate(availabilityAlreadySaved.getStartDate().minus(1, ChronoUnit.DAYS));
            availabilityToSave.setEndDate(availabilityAlreadySaved.getEndDate().plus(1, ChronoUnit.DAYS));

            objectToTest.save(availabilityToSave);
        }
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
        private AvailabilityDto.AvailabilityCreate availabilityCreateDto = AvailabilityDtoMother.createSample().build();
        private UserInfo userToLink = UserInfoMother.sample().build();
        private Mission missionToLink = MissionMother.sampleTeamEngagement().build();
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
        void create_returnedAvailabilityShouldContainLinkedEntities() {
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
        private AvailabilityDto.AvailabilityUpdate availabilityUpdateDto = AvailabilityDtoMother.updateSample().build();
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

    @Nested
    class isAvailabilityOwnerTest {
        private Availability availabilityFound = AvailabilityMother.sample().build();
        private Long parameterId = 1L;

        @BeforeEach
        void setup() {
            Mockito.when(availabilityRepository.findById(parameterId)).thenReturn(Optional.ofNullable(availabilityFound));
        }

        @Test
        void isAvailabilityOwner_whenUserIsNotOwner_returnFalse() {
            Mockito.when(securityService.isSameUsernameThanAuthenticatedUser(availabilityFound.getUserInfo().getUsername())).thenReturn(false);

            boolean result = objectToTest.isAvailabilityOwner(parameterId);

            Assertions.assertThat(result).isFalse();
        }

        @Test
        void isAvailabilityOwner_whenUserIsOwner_returnTrue() {
            Mockito.when(securityService.isSameUsernameThanAuthenticatedUser(availabilityFound.getUserInfo().getUsername())).thenReturn(true);

            boolean result = objectToTest.isAvailabilityOwner(parameterId);

            Assertions.assertThat(result).isTrue();
        }

        @Test
        void isAvailabilityOwner_noAvailabilityFound_returnFalse() {
            Mockito.when(availabilityRepository.findById(parameterId)).thenReturn(Optional.empty());

            boolean result = objectToTest.isAvailabilityOwner(parameterId);

            Assertions.assertThat(result).isFalse();
        }
    }
}
