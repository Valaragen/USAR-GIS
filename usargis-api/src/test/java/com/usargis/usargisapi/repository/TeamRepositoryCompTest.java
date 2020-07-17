package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Mission;
import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.testutils.objectMother.MissionMother;
import com.usargis.usargisapi.testutils.objectMother.TeamMother;
import com.usargis.usargisapi.testutils.objectMother.UserInfoMother;
import lombok.extern.slf4j.Slf4j;
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
class TeamRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TeamRepository objectToTest;

    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private Team sampleTeam = TeamMother.sample().mission(sampleMission).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleMission);
        entityManager.persist(sampleTeam);
    }

    @Test
    void findAll_shouldFindAllTeamsInDb() {
        List<Team> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindTeamInDbById() {
        Team teamToFind =  sampleTeam;

        Optional<Team> result = objectToTest.findById(teamToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(teamToFind);
    }

    @Test
    void save_shouldAddTeamInDb() {
        Team teamToSave = TeamMother.sample()
                .name("test")
                .mission(sampleMission)
                .build();

        objectToTest.save(teamToSave);

        Assertions.assertThat(entityManager.contains(teamToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Team teamToDelete =  sampleTeam;

        objectToTest.delete(teamToDelete);

        Assertions.assertThat(entityManager.contains(teamToDelete)).isFalse();
    }

}
