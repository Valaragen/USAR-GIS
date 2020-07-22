package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.service.contract.InscriptionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.InscriptionDtoMother;
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

class InscriptionControllerTest {

    private InscriptionController objectToTest;

    private InscriptionService inscriptionService = Mockito.mock(InscriptionService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new InscriptionController(inscriptionService, modelMapperService);
    }

    @Nested
    class findAllInscriptionsTest {
        private final List<Inscription> inscriptionsFound = Arrays.asList(new Inscription(), new Inscription());

        @Test
        void findAllInscriptions_shouldCallServiceLayer() {
            Mockito.when(inscriptionService.findAll()).thenReturn(inscriptionsFound);

            objectToTest.findAllInscriptions();

            Mockito.verify(inscriptionService).findAll();
        }

        @Test
        void findAllInscriptions_noInscriptionFound_throwNotFoundException() {
            Mockito.when(inscriptionService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllInscriptions())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_INSCRIPTION_FOUND);
        }

        @Test
        void findAllInscriptions_shouldConvertInscriptionsToListOfResponseDto() {
            Mockito.when(inscriptionService.findAll()).thenReturn(inscriptionsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Inscription.class), Mockito.any())).thenReturn(new InscriptionDto.Response());

            objectToTest.findAllInscriptions();

            Mockito.verify(modelMapperService, Mockito.times(inscriptionsFound.size())).map(Mockito.any(Inscription.class), Mockito.any());
        }

        @Test
        void findAllInscriptions_inscriptionFound_returnStatusOkWithListOfInscriptionsResponseDto() {
            Mockito.when(inscriptionService.findAll()).thenReturn(inscriptionsFound);
            Mockito.when(modelMapperService.map(Mockito.any(Inscription.class), Mockito.any())).thenReturn(new InscriptionDto.Response());

            ResponseEntity<List<InscriptionDto.Response>> result = objectToTest.findAllInscriptions();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(inscriptionsFound.size());
        }
    }

    @Nested
    class getInscriptionByIdTest {
        private final Long inscriptionIdToFind = 1L;
        private final Inscription inscriptionFound = new Inscription();
        private final InscriptionDto.Response inscriptionResponseDto = new InscriptionDto.Response();

        @Test
        void getInscriptionById_shouldCallServiceLayer() {
            Mockito.when(inscriptionService.findById(inscriptionIdToFind)).thenReturn(Optional.of(inscriptionFound));

            objectToTest.getInscriptionById(inscriptionIdToFind);

            Mockito.verify(inscriptionService).findById(inscriptionIdToFind);
        }

        @Test
        void getInscriptionById_noInscriptionFound_throwNotFoundException() {
            Mockito.when(inscriptionService.findById(inscriptionIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getInscriptionById(inscriptionIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, inscriptionIdToFind));
        }

        @Test
        void getInscriptionById_shouldConvertInscriptionToResponseDto() {
            Mockito.when(inscriptionService.findById(inscriptionIdToFind)).thenReturn(Optional.of(inscriptionFound));
            Mockito.when(modelMapperService.map(inscriptionFound, InscriptionDto.Response.class)).thenReturn(inscriptionResponseDto);

            objectToTest.getInscriptionById(inscriptionIdToFind);

            Mockito.verify(modelMapperService).map(inscriptionFound, InscriptionDto.Response.class);
        }

        @Test
        void getInscriptionById_inscriptionFound_returnStatusOkAndInscriptionResponseDto() {
            Mockito.when(inscriptionService.findById(inscriptionIdToFind)).thenReturn(Optional.of(inscriptionFound));
            Mockito.when(modelMapperService.map(inscriptionFound, InscriptionDto.Response.class)).thenReturn(inscriptionResponseDto);

            ResponseEntity<InscriptionDto.Response> result = objectToTest.getInscriptionById(inscriptionIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(inscriptionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(InscriptionDto.Response.class);
        }
    }

    @Nested
    class createNewInscriptionTest {
        private final InscriptionDto.PostRequest inscriptionToSave = InscriptionDtoMother.postRequestSample().build();
        private final Inscription newInscription = new Inscription();
        private final InscriptionDto.Response inscriptionResponseDto = new InscriptionDto.Response();

        @Test
        void createNewInscription_shouldCallServiceLayer() {
            Mockito.when(inscriptionService.create(inscriptionToSave)).thenReturn(newInscription);

            objectToTest.createNewInscription(inscriptionToSave);

            Mockito.verify(inscriptionService).create(inscriptionToSave);
        }

        @Test
        void createNewInscription_shouldConvertInscriptionToResponseDto() {
            Mockito.when(inscriptionService.create(inscriptionToSave)).thenReturn(newInscription);
            Mockito.when(modelMapperService.map(newInscription, InscriptionDto.Response.class)).thenReturn(inscriptionResponseDto);

            objectToTest.createNewInscription(inscriptionToSave);

            Mockito.verify(modelMapperService).map(newInscription, InscriptionDto.Response.class);
        }

        @Test
        void createNewInscription_inscriptionCreated_returnStatusCreatedAndInscriptionResponseDto() {
            Mockito.when(inscriptionService.create(inscriptionToSave)).thenReturn(newInscription);
            Mockito.when(modelMapperService.map(newInscription, InscriptionDto.Response.class)).thenReturn(inscriptionResponseDto);

            ResponseEntity<InscriptionDto.Response> result = objectToTest.createNewInscription(inscriptionToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(inscriptionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(InscriptionDto.Response.class);
        }
    }

    @Nested
    class updateInscriptionTest {
        private final Long inscriptionId = 1L;
        private final InscriptionDto.PostRequest inscriptionToUpdate = InscriptionDtoMother.postRequestSample().build();
        private final Inscription updateInscription = new Inscription();
        private final InscriptionDto.Response inscriptionResponseDto = new InscriptionDto.Response();

        @Test
        void updateInscriptionTest_shouldCallServiceLayer() {
            Mockito.when(inscriptionService.update(inscriptionId, inscriptionToUpdate)).thenReturn(updateInscription);

            objectToTest.updateInscription(inscriptionId, inscriptionToUpdate);

            Mockito.verify(inscriptionService).update(inscriptionId, inscriptionToUpdate);
        }

        @Test
        void updateInscriptionTest_shouldConvertInscriptionToResponseDto() {
            Mockito.when(inscriptionService.update(inscriptionId, inscriptionToUpdate))
                    .thenReturn(updateInscription);
            Mockito.when(modelMapperService.map(updateInscription, InscriptionDto.Response.class))
                    .thenReturn(inscriptionResponseDto);

            objectToTest.updateInscription(inscriptionId, inscriptionToUpdate);

            Mockito.verify(modelMapperService).map(updateInscription, InscriptionDto.Response.class);
        }

        @Test
        void updateInscriptionTest_inscriptionCreated_returnStatusOkAndInscriptionResponseDto() {
            Mockito.when(inscriptionService.update(inscriptionId, inscriptionToUpdate))
                    .thenReturn(updateInscription);
            Mockito.when(modelMapperService.map(updateInscription, InscriptionDto.Response.class))
                    .thenReturn(inscriptionResponseDto);

            ResponseEntity<InscriptionDto.Response> result =
                    objectToTest.updateInscription(inscriptionId, inscriptionToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(inscriptionResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(InscriptionDto.Response.class);
        }
    }

    @Nested
    class deleteInscriptionTest {
        private final Long inscriptionToDeleteId = 1L;
        private final Inscription foundInscriptionToDelete = new Inscription();

        @Test
        void deleteInscription_shouldCallServiceLayer() {
            Mockito.when(inscriptionService.findById(inscriptionToDeleteId)).thenReturn(Optional.of(foundInscriptionToDelete));
            Mockito.doNothing().when(inscriptionService).delete(foundInscriptionToDelete);

            objectToTest.deleteInscription(inscriptionToDeleteId);

            Mockito.verify(inscriptionService).findById(inscriptionToDeleteId);
            Mockito.verify(inscriptionService).delete(foundInscriptionToDelete);
        }

        @Test
        void deleteInscription_inscriptionDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(inscriptionService.findById(inscriptionToDeleteId)).thenReturn(Optional.of(foundInscriptionToDelete));
            Mockito.doNothing().when(inscriptionService).delete(foundInscriptionToDelete);

            ResponseEntity result = objectToTest.deleteInscription(inscriptionToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
