package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMemberMother;
import com.usargis.usargisapi.util.objectMother.model.TeamMother;
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
class TeamMemberRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TeamMemberRepository objectToTest;

    private UserInfo sampleUser = UserInfoMother.sample().build();
    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private Team sampleTeam = TeamMother.sample().mission(sampleMission).build();
    private TeamMember sampleTeamMember = TeamMemberMother.sample()
            .team(sampleTeam)
            .userInfo(sampleUser).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleUser);
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleMission);
        entityManager.persist(sampleTeam);
        entityManager.persist(sampleTeamMember);
    }

    @Test
    void findAll_shouldFindAllTeamMembersInDb() {
        List<TeamMember> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindTeamMemberInDbById() {
        TeamMember teamMemberToFind = sampleTeamMember;

        Optional<TeamMember> result = objectToTest.findById(teamMemberToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(teamMemberToFind);
    }

    @Test
    void save_shouldAddTeamMemberInDb() {
        TeamMember teamMemberToSave = TeamMemberMother.sample()
                .team(sampleTeam)
                .userInfo(sampleMissionAuthor)
                .build();

        objectToTest.save(teamMemberToSave);

        Assertions.assertThat(entityManager.contains(teamMemberToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        TeamMember teamMemberToDelete = sampleTeamMember;

        objectToTest.delete(teamMemberToDelete);

        Assertions.assertThat(entityManager.contains(teamMemberToDelete)).isFalse();
    }

}
