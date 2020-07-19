package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.testutils.objectMother.model.MissionMother;
import com.usargis.usargisapi.testutils.objectMother.model.UserInfoMother;
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
class MissionRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MissionRepository objectToTest;

    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleMission);
    }

    @Test
    void findAll_shouldFindAllMissionsInDb() {
        List<Mission> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindMissionInDbById() {
        Mission missionToFind = sampleMission;

        Optional<Mission> result = objectToTest.findById(missionToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(missionToFind);
    }

    @Test
    void save_shouldAddMissionInDb() {
        Mission missionToSave = MissionMother.sampleFinished()
                .author(sampleMissionAuthor)
                .build();

        objectToTest.save(missionToSave);

        Assertions.assertThat(entityManager.contains(missionToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Mission missionToDelete = sampleMission;

        objectToTest.delete(missionToDelete);

        Assertions.assertThat(entityManager.contains(missionToDelete)).isFalse();
    }

}
