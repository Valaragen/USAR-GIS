package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.*;
import com.usargis.usargisapi.repository.InscriptionRepository;
import com.usargis.usargisapi.service.contract.EventService;
import com.usargis.usargisapi.service.contract.InscriptionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.InscriptionDtoMother;
import com.usargis.usargisapi.util.objectMother.model.InscriptionMother;
import com.usargis.usargisapi.util.objectMother.model.EventMother;
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

class InscriptionServiceImplTest {

    private InscriptionService objectToTest;

    private InscriptionRepository inscriptionRepository = Mockito.mock(InscriptionRepository.class);
    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private EventService eventService = Mockito.mock(EventService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new InscriptionServiceImpl(inscriptionRepository, userInfoService,
                eventService, modelMapperService);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<Inscription> returnedInscriptionList = Collections.singletonList(new Inscription());
        Mockito.when(inscriptionRepository.findAll()).thenReturn(returnedInscriptionList);

        List<Inscription> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedInscriptionList);
        Mockito.verify(inscriptionRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        Inscription inscriptionFound = new Inscription();
        Optional<Inscription> expectedResult = Optional.of(inscriptionFound);
        Long inscriptionIdToFind = 1L;
        Mockito.when(inscriptionRepository.findById(inscriptionIdToFind)).thenReturn(expectedResult);

        Optional<Inscription> result = objectToTest.findById(inscriptionIdToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(inscriptionRepository).findById(inscriptionIdToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnInscription() {
        Inscription inscriptionToSave = new Inscription();
        Mockito.when(inscriptionRepository.save(inscriptionToSave)).thenReturn(inscriptionToSave);

        Inscription result = objectToTest.save(inscriptionToSave);

        Assertions.assertThat(result).isEqualTo(inscriptionToSave);
        Mockito.verify(inscriptionRepository).save(inscriptionToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        Inscription inscriptionToDelete = new Inscription();
        Mockito.doNothing().when(inscriptionRepository).delete(inscriptionToDelete);

        objectToTest.delete(inscriptionToDelete);

        Mockito.verify(inscriptionRepository).delete(inscriptionToDelete);
    }

    @Nested
    class createTest {
        private InscriptionDto.PostRequest inscriptionCreateDto = InscriptionDtoMother.postRequestSample().build();
        private UserInfo userToLink = UserInfoMother.sample().build();
        private Event eventToLink = EventMother.sampleFinished().build();
        private Inscription savedInscription = InscriptionMother.sampleValidated().build();

        @BeforeEach
        void setup() {
            Mockito.when(userInfoService.findByUsername(inscriptionCreateDto.getUserInfoUsername())).thenReturn(Optional.of(userToLink));
            Mockito.when(eventService.findById(inscriptionCreateDto.getEventId())).thenReturn(Optional.of(eventToLink));
            Mockito.when(inscriptionRepository.save(Mockito.any(Inscription.class))).thenReturn(savedInscription);
        }

        @Test
        void create_noUserForGivenUsername_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(inscriptionCreateDto.getUserInfoUsername())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(inscriptionCreateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, inscriptionCreateDto.getUserInfoUsername()));
        }

        @Test
        void create_noEventForGivenId_throwNotFoundException() {
            Mockito.when(eventService.findById(inscriptionCreateDto.getEventId())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.create(inscriptionCreateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_EVENT_FOUND_FOR_ID, inscriptionCreateDto.getEventId()));
        }

        @Test
        void create_shouldMapDtoInInscription() {
            objectToTest.create(inscriptionCreateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(InscriptionDto.class), Mockito.any(Inscription.class));
        }

        @Test
        void create_shouldSaveNewEntity() {
            objectToTest.create(inscriptionCreateDto);

            Mockito.verify(inscriptionRepository).save(Mockito.any(Inscription.class));
        }

        @Test
        void create_shouldReturnSavedInscription() {
            Inscription result = objectToTest.create(inscriptionCreateDto);

            Assertions.assertThat(result).isEqualTo(savedInscription);
        }

        @Test
        void create_returnedAbilityShouldContainLinkedEntities() {
            Mockito.when(inscriptionRepository.save(Mockito.any(Inscription.class))).then(AdditionalAnswers.returnsFirstArg());

            Inscription result = objectToTest.create(inscriptionCreateDto);

            Assertions.assertThat(result.getUserInfo()).isEqualTo(userToLink);
            Assertions.assertThat(result.getEvent()).isEqualTo(eventToLink);
        }
    }

    @Nested
    class updateTest {
        private Long givenId = 1L;
        private Inscription inscriptionToUpdate = InscriptionMother.sampleValidated().build();
        private InscriptionDto.PostRequest inscriptionUpdateDto = InscriptionDtoMother.postRequestSample().build();
        private Inscription savedInscription = InscriptionMother.sampleValidated().build();

        @BeforeEach
        void setup() {
            Mockito.when(inscriptionRepository.findById(givenId)).thenReturn(Optional.ofNullable(inscriptionToUpdate));
            Mockito.when(inscriptionRepository.save(Mockito.any(Inscription.class))).thenReturn(savedInscription);
        }

        @Test
        void update_noInscriptionForGivenId_throwNotFoundException() {
            Mockito.when(inscriptionRepository.findById(givenId)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> {
                objectToTest.update(givenId, inscriptionUpdateDto);
            }).isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, givenId));
        }

        @Test
        void update_shouldMapDtoInInscription() {
            objectToTest.update(givenId, inscriptionUpdateDto);

            Mockito.verify(modelMapperService).map(Mockito.any(InscriptionDto.class), Mockito.any(Inscription.class));
        }

        @Test
        void update_shouldSaveNewEntity() {
            objectToTest.update(givenId, inscriptionUpdateDto);

            Mockito.verify(inscriptionRepository).save(Mockito.any(Inscription.class));
        }

        @Test
        void update_shouldReturnSavedInscription() {
            Inscription result = objectToTest.update(givenId, inscriptionUpdateDto);

            Assertions.assertThat(result).isEqualTo(savedInscription);
        }
    }
}
