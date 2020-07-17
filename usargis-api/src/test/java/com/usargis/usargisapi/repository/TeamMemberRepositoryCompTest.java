package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Mission;
import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.model.TeamMember;
import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.model.embeddable.TeamMemberId;
import com.usargis.usargisapi.testutils.objectMother.MissionMother;
import com.usargis.usargisapi.testutils.objectMother.TeamMemberMother;
import com.usargis.usargisapi.testutils.objectMother.TeamMother;
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
class TeamMemberRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TeamMemberRepository objectToTest;

    private UserInfo sampleUser = UserInfoMother.sample().build();
    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private Team sampleTeam = TeamMother.sample().mission(sampleMission).build();
    private TeamMember sampleTeamMember = TeamMemberMother.sample().id(new TeamMemberId(sampleTeam, sampleUser)).build();

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
                .id(new TeamMemberId(sampleTeam, sampleMissionAuthor))
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
