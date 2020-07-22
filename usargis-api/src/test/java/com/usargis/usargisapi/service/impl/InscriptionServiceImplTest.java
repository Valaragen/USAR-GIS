package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.repository.InscriptionRepository;
import com.usargis.usargisapi.service.contract.InscriptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class InscriptionServiceImplTest {

    private InscriptionService objectToTest;

    private InscriptionRepository inscriptionRepository = Mockito.mock(InscriptionRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new InscriptionServiceImpl(inscriptionRepository);
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

}
