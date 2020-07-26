package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.util.objectMother.model.EventMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
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
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
class EventRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EventRepository objectToTest;

    private UserInfo sampleEventAuthor = UserInfoMother.sampleAuthor().build();
    private Event sampleEvent = EventMother.sampleFinished().author(sampleEventAuthor).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleEventAuthor);
        entityManager.persist(sampleEvent);
    }

    @Test
    void findAll_shouldFindAllEventsInDb() {
        List<Event> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindEventInDbById() {
        Event eventToFind = sampleEvent;

        Optional<Event> result = objectToTest.findById(eventToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(eventToFind);
    }

    @Test
    void save_shouldAddEventInDb() {
        Event eventToSave = EventMother.sampleFinished()
                .author(sampleEventAuthor)
                .build();

        objectToTest.save(eventToSave);

        Assertions.assertThat(entityManager.contains(eventToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Event eventToDelete = sampleEvent;

        objectToTest.delete(eventToDelete);

        Assertions.assertThat(entityManager.contains(eventToDelete)).isFalse();
    }

}
