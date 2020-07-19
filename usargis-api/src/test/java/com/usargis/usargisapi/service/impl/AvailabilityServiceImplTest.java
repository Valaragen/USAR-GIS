package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.repository.AvailabilityRepository;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class AvailabilityServiceImplTest {

    private AvailabilityService objectToTest;

    private AvailabilityRepository availabilityRepository = Mockito.mock(AvailabilityRepository.class);

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        objectToTest = new AvailabilityServiceImpl(availabilityRepository);
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
}
