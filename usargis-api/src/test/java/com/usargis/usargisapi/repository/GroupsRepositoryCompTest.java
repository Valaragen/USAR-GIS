package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.testutils.objectMother.GroupMother;
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
class GroupsRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroupRepository objectToTest;

    private Group sampleGroup = GroupMother.sample().build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleGroup);
    }

    @Test
    void findAll_shouldFindAllGroupsInDb() {
        List<Group> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindGroupInDbById() {
        Group groupToFind = sampleGroup;

        Optional<Group> result = objectToTest.findById(groupToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(groupToFind);
    }

    @Test
    void save_shouldAddGroupInDb() {
        Group groupToSave = GroupMother.sample()
                .name("group-name")
                .build();

        objectToTest.save(groupToSave);

        Assertions.assertThat(entityManager.contains(groupToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Group groupToDelete = sampleGroup;

        objectToTest.delete(groupToDelete);

        Assertions.assertThat(entityManager.contains(groupToDelete)).isFalse();
    }

}
