package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Event;
import com.usargis.usargisapi.model.Inscription;
import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.model.embeddable.InscriptionId;
import com.usargis.usargisapi.testutils.objectMother.EventMother;
import com.usargis.usargisapi.testutils.objectMother.InscriptionMother;
import com.usargis.usargisapi.testutils.objectMother.UserInfoMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
class InscriptionRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private InscriptionRepository objectToTest;

    private UserInfo sampleUser = UserInfoMother.sample().build();
    private UserInfo sampleEventAuthor = UserInfoMother.sampleAuthor().build();
    private Event sampleFinishedEvent = EventMother.sampleFinished().author(sampleEventAuthor).build();
    private Inscription sampleValidatedInscription = InscriptionMother.sampleValidated()
            .id(new InscriptionId(sampleUser, sampleFinishedEvent)).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleUser);
        entityManager.persist(sampleEventAuthor);
        entityManager.persist(sampleFinishedEvent);
        entityManager.persist(sampleValidatedInscription);
    }

    @Test
    void findAll_shouldFindAllInscriptionsInDb() {
        List<Inscription> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindInscriptionInDbById() {
        Inscription inscriptionToFind = sampleValidatedInscription;

        Optional<Inscription> result = objectToTest.findById(inscriptionToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(inscriptionToFind);
    }

    @Test
    void save_shouldAddInscriptionInDb() {
        Inscription inscriptionToSave = InscriptionMother.sampleValidated()
                .id(new InscriptionId(sampleEventAuthor, sampleFinishedEvent))
                .build();

        objectToTest.save(inscriptionToSave);

        Assertions.assertThat(entityManager.contains(inscriptionToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Inscription inscriptionToDelete = sampleValidatedInscription;

        objectToTest.delete(inscriptionToDelete);

        Assertions.assertThat(entityManager.contains(inscriptionToDelete)).isFalse();
    }

}
