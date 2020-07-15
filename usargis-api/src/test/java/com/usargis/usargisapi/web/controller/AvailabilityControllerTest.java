package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.search.AvailabilitySearch;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.impl.AvailabilityServiceImpl;
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

class AvailabilityControllerTest {

    private AvailabilityController objectToTest;

    private AvailabilityService availabilityService = Mockito.mock(AvailabilityServiceImpl.class);

    @BeforeEach
    void setup() {
        objectToTest = new AvailabilityController(availabilityService);
    }

    @Nested
    class searchAllAvailabilitiesTest {
        private final AvailabilitySearch searchParameters = new AvailabilitySearch();
        @Test
        void searchAllAvailabilities_shouldCallServiceLayer() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(Collections.singletonList(new Availability()));

            objectToTest.searchForAvailabilities(searchParameters);

            Mockito.verify(availabilityService).searchAll(searchParameters);
        }

        @Test
        void searchAllAvailabilities_noAvailabilityFound_throwNotFoundException() {
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.searchForAvailabilities(searchParameters))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_AVAILABILITIES_FOUND);
        }

        @Test
        void searchAllAvailabilities_availabilityFound_returnResponseEntityWithAvailabilityList() {
            List<Availability> returnedAvailabilityList = Collections.singletonList(new Availability());
            Mockito.when(availabilityService.searchAll(searchParameters)).thenReturn(returnedAvailabilityList);

            ResponseEntity result = objectToTest.searchForAvailabilities(searchParameters);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedAvailabilityList);
        }
    }

    @Nested
    class getAvailabilityByIdTest {
        private final Long availabilityIdToFind = 1L;
        private final Availability availabilityFound = new Availability();

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
        void getAvailabilityById_availabilityFound_returnResponseEntityWithAvailabilityAndStatusOk() {
            Mockito.when(availabilityService.findById(availabilityIdToFind)).thenReturn(Optional.of(availabilityFound));

            ResponseEntity result = objectToTest.getAvailabilityById(availabilityIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(availabilityFound);
        }
    }

    @Nested
    class createNewAvailabilityTest {
        private final Availability availabilityToSave = new Availability();
        private final Availability savedAvailability = new Availability();

        @Test
        void createNewAvailability_shouldCallServiceLayer() {
            Mockito.when(availabilityService.save(availabilityToSave)).thenReturn(savedAvailability);

            objectToTest.createNewAvailability(availabilityToSave);

            Mockito.verify(availabilityService).save(availabilityToSave);
        }

        @Test
        void createNewAvailability_availabilityCreated_returnResponseEntityWithAvailabilityAndStatusCreated() {
            Mockito.when(availabilityService.save(availabilityToSave)).thenReturn(savedAvailability);

            ResponseEntity result = objectToTest.createNewAvailability(availabilityToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedAvailability);
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
                    .isEqualTo(null);
        }
    }

}
