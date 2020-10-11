package com.usargis.usargisapi.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.AvailabilityDtoMother;
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

class AvailabilityControllerTest {

    private AvailabilityController objectToTest;

    private AvailabilityService availabilityService = Mockito.mock(AvailabilityService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new AvailabilityController(availabilityService, modelMapperService);
    }

    @Nested
    class searchAllAvailabilitiesTest {
        private final AvailabilitySearch searchParameters = new AvailabilitySearch();
        private final List<Availability> availabilitiesFound = Arrays.asList(new Availability(), new Availability());

        @Test
        void searchAllAvailabilities_shouldCallServiceLayer() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(availabilitiesFound);

            objectToTest.searchForAvailabilities(searchParameters);

            Mockito.verify(availabilityService).searchAll(searchParameters);
        }

        @Test
        void searchAllAvailabilities_noAvailabilityFound_throwNotFoundException() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.searchForAvailabilities(searchParameters))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_AVAILABILITY_FOUND);
        }

        @Test
        void searchAllAvailabilities_shouldConvertAvailabilitiesToListOfResponseDto() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(availabilitiesFound);
            Mockito.when(modelMapperService.map(Mockito.any(Availability.class), Mockito.any())).thenReturn(new AvailabilityDto.AvailabilityResponse());

            objectToTest.searchForAvailabilities(searchParameters);

            Mockito.verify(modelMapperService, Mockito.times(availabilitiesFound.size())).map(Mockito.any(Availability.class), Mockito.any());
        }

        @Test
        void searchAllAvailabilities_availabilityFound_returnStatusOkWithListOfAvailabilitiesResponseDto() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(availabilitiesFound);
            Mockito.when(modelMapperService.map(Mockito.any(Availability.class), Mockito.any())).thenReturn(new AvailabilityDto.AvailabilityResponse());

            ResponseEntity<List<AvailabilityDto.AvailabilityResponse>> result = objectToTest.searchForAvailabilities(searchParameters);

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(availabilitiesFound.size());
        }
    }

    @Nested
    class getAvailabilityByIdTest {
        private final Long availabilityIdToFind = 1L;
        private final Availability availabilityFound = new Availability();
        private final AvailabilityDto.AvailabilityResponse availabilityResponseDto = new AvailabilityDto.AvailabilityResponse();

        @Test
        void getAvailabilityById_shouldCallServiceLayer() {
            Mockito.when(availabilityService.findById(availabilityIdToFind)).thenReturn(Optional.of(availabilityFound));

            objectToTest.getAvailabilityById(availabilityIdToFind);

            Mockito.verify(availabilityService).findById(availabilityIdToFind);
        }

        @Test
        void getAvailabilityById_noAvailabilityFound_throwNotFoundException() {
            Mockito.when(availabilityService.findById(availabilityIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getAvailabilityById(availabilityIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_AVAILABILITY_FOUND_FOR_ID, availabilityIdToFind));
        }

        @Test
        void getAvailabilityById_shouldConvertAvailabilityToResponseDto() {
            Mockito.when(availabilityService.findById(availabilityIdToFind)).thenReturn(Optional.of(availabilityFound));
            Mockito.when(modelMapperService.map(availabilityFound, AvailabilityDto.AvailabilityResponse.class)).thenReturn(availabilityResponseDto);

            objectToTest.getAvailabilityById(availabilityIdToFind);

            Mockito.verify(modelMapperService).map(availabilityFound, AvailabilityDto.AvailabilityResponse.class);
        }

        @Test
        void getAvailabilityById_availabilityFound_returnStatusOkAndAvailabilityResponseDto() {
            Mockito.when(availabilityService.findById(availabilityIdToFind)).thenReturn(Optional.of(availabilityFound));
            Mockito.when(modelMapperService.map(availabilityFound, AvailabilityDto.AvailabilityResponse.class)).thenReturn(availabilityResponseDto);

            ResponseEntity<AvailabilityDto.AvailabilityResponse> result = objectToTest.getAvailabilityById(availabilityIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(availabilityResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(AvailabilityDto.AvailabilityResponse.class);
        }
    }

    @Nested
    class createNewAvailabilityTest {
        private final AvailabilityDto.AvailabilityCreate availabilityToSave = AvailabilityDtoMother.createSample().build();
        private final Availability newAvailability = new Availability();
        private final AvailabilityDto.AvailabilityResponse availabilityResponseDto = new AvailabilityDto.AvailabilityResponse();

        @Test
        void createNewAvailability_shouldCallServiceLayer() {
            Mockito.when(availabilityService.create(availabilityToSave)).thenReturn(newAvailability);

            objectToTest.createNewAvailability(availabilityToSave);

            Mockito.verify(availabilityService).create(availabilityToSave);
        }

        @Test
        void createNewAvailability_shouldConvertAvailabilityToResponseDto() {
            Mockito.when(availabilityService.create(availabilityToSave)).thenReturn(newAvailability);
            Mockito.when(modelMapperService.map(newAvailability, AvailabilityDto.AvailabilityResponse.class)).thenReturn(availabilityResponseDto);

            objectToTest.createNewAvailability(availabilityToSave);

            Mockito.verify(modelMapperService).map(newAvailability, AvailabilityDto.AvailabilityResponse.class);
        }

        @Test
        void createNewAvailability_availabilityCreated_returnStatusCreatedAndAvailabilityResponseDto() {
            Mockito.when(availabilityService.create(availabilityToSave)).thenReturn(newAvailability);
            Mockito.when(modelMapperService.map(newAvailability, AvailabilityDto.AvailabilityResponse.class)).thenReturn(availabilityResponseDto);

            ResponseEntity<AvailabilityDto.AvailabilityResponse> result = objectToTest.createNewAvailability(availabilityToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(availabilityResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(AvailabilityDto.AvailabilityResponse.class);
        }
    }

    @Nested
    class updateAvailabilityTest {
        private final Long availabilityId = 1L;
        private final AvailabilityDto.AvailabilityUpdate availabilityToUpdate = AvailabilityDtoMother.updateSample().build();
        private final Availability updateAvailability = new Availability();
        private final AvailabilityDto.AvailabilityResponse availabilityResponseDto = new AvailabilityDto.AvailabilityResponse();

        @Test
        void updateAvailabilityTest_shouldCallServiceLayer() {
            Mockito.when(availabilityService.update(availabilityId, availabilityToUpdate)).thenReturn(updateAvailability);

            objectToTest.updateAvailability(availabilityId, availabilityToUpdate);

            Mockito.verify(availabilityService).update(availabilityId, availabilityToUpdate);
        }

        @Test
        void updateAvailabilityTest_shouldConvertAvailabilityToResponseDto() {
            Mockito.when(availabilityService.update(availabilityId, availabilityToUpdate))
                    .thenReturn(updateAvailability);
            Mockito.when(modelMapperService.map(updateAvailability, AvailabilityDto.AvailabilityResponse.class))
                    .thenReturn(availabilityResponseDto);

            objectToTest.updateAvailability(availabilityId, availabilityToUpdate);

            Mockito.verify(modelMapperService).map(updateAvailability, AvailabilityDto.AvailabilityResponse.class);
        }

        @Test
        void updateAvailabilityTest_availabilityCreated_returnStatusOkAndAvailabilityResponseDto() {
            Mockito.when(availabilityService.update(availabilityId, availabilityToUpdate))
                    .thenReturn(updateAvailability);
            Mockito.when(modelMapperService.map(updateAvailability, AvailabilityDto.AvailabilityResponse.class))
                    .thenReturn(availabilityResponseDto);

            ResponseEntity<AvailabilityDto.AvailabilityResponse> result =
                    objectToTest.updateAvailability(availabilityId, availabilityToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(availabilityResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(AvailabilityDto.AvailabilityResponse.class);
        }
    }

    @Nested
    class deleteAvailabilityTest {
        private final Long availabilityToDeleteId = 1L;
        private final Availability foundAvailabilityToDelete = new Availability();

        @Test
        void deleteAvailability_shouldCallServiceLayer() {
            Mockito.when(availabilityService.findById(availabilityToDeleteId)).thenReturn(Optional.of(foundAvailabilityToDelete));
            Mockito.doNothing().when(availabilityService).delete(foundAvailabilityToDelete);

            objectToTest.deleteAvailability(availabilityToDeleteId);

            Mockito.verify(availabilityService).findById(availabilityToDeleteId);
            Mockito.verify(availabilityService).delete(foundAvailabilityToDelete);
        }

        @Test
        void deleteAvailability_availabilityDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(availabilityService.findById(availabilityToDeleteId)).thenReturn(Optional.of(foundAvailabilityToDelete));
            Mockito.doNothing().when(availabilityService).delete(foundAvailabilityToDelete);

            ResponseEntity result = objectToTest.deleteAvailability(availabilityToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }

    @Nested
    class patchAvailabilityTest {
        private final Long availabilityId = 1L;
        private final Availability patchedAvailability = new Availability();
        private final AvailabilityDto.AvailabilityResponse availabilityResponseDto = new AvailabilityDto.AvailabilityResponse();
        private final JsonPatch jsonPatch = new JsonPatch(new ArrayList<>());

        @BeforeEach
        void setup() throws JsonPatchException {
            Mockito.when(availabilityService.patch(availabilityId, jsonPatch)).thenReturn(patchedAvailability);
        }

        @Test
        void patchAvailability_shouldCallServiceLayer() throws JsonPatchException {
            objectToTest.patchAvailability(availabilityId, jsonPatch);

            Mockito.verify(availabilityService).patch(availabilityId, jsonPatch);
        }

        @Test
        void patchAvailability_availabilityPatched_returnStatusOkAndAvailabilityResponseDto() throws JsonPatchException {
            Mockito.when(modelMapperService.map(patchedAvailability, AvailabilityDto.AvailabilityResponse.class)).thenReturn(availabilityResponseDto);

            ResponseEntity<AvailabilityDto.AvailabilityResponse> result =
                    objectToTest.patchAvailability(availabilityId, jsonPatch);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(availabilityResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(AvailabilityDto.AvailabilityResponse.class);
        }
    }
}
