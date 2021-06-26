package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.MissionStatus;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.MissionSearchCriteria;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
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
class MissionRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MissionRepository objectToTest;

    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private UserInfo sampleMissionAuthor2 = UserInfoMother.sample().build();
    private Mission sampleMission2 = MissionMother.sampleTeamEngagement().author(sampleMissionAuthor2).build();;

    @BeforeEach
    void setup() {
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleMission);
        entityManager.persist(sampleMissionAuthor2);
        entityManager.persist(sampleMission2);
    }

    @Test
    void findAll_shouldFindAllMissionsInDb() {
        List<Mission> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
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

    //Search by ID
    @Test
    void searchAll_givenCriteriaId_shouldFindById() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setMissionId(sampleMission.getId());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaIdWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setMissionId(999L);

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //Search by name
    @Test
    void searchAll_givenCriteriaName_shouldFindByName() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setName(sampleMission.getName());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaNameWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setName("unknownName");

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //Mission Status
    @Test
    void searchAll_givenCriteriaStatus_shouldFindByStatus() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setMissionStatus(sampleMission.getStatus());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaStatusWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setMissionStatus(MissionStatus.CANCELLED);

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //After date
    @Test
    void searchAll_givenCriteriaAfterDate_shouldFindMissionWithAPosteriorStartDate() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAfterDate(sampleMission.getStartDate().minusDays(1L));

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaAfterDate_shouldFindMissionWithAnEqualStartDate() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAfterDate(sampleMission.getStartDate());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaAfterDateWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAfterDate(sampleMission.getStartDate().plusDays(1L));

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //Before date
    @Test
    void searchAll_givenCriteriaBeforeDate_shouldFindMissionWithAnAnteriorStartDate() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setBeforeDate(sampleMission.getEndDate().plusDays(1L));

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaBeforeDate_shouldFindMissionWithAnEqualStartDate() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setBeforeDate(sampleMission.getEndDate());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaBeforeDateWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setBeforeDate(sampleMission.getStartDate().minusDays(1L));

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //Address
    @Test
    void searchAll_givenCriteriaAddress_shouldFindMissionByAddress() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAddress(sampleMission.getAddress());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaAddressWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAddress("unknownAddress");

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

    //Author username
    @Test
    void searchAll_givenCriteriaAuthorUsername_shouldFindMissionByAuthorUsername() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAuthorUsername(sampleMission.getAuthor().getUsername());

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);

        Assertions.assertThat(result).contains(sampleMission);
    }

    @Test
    void searchAll_givenCriteriaAuthorUsernameWithoutMatchInDb_resultShouldBeEmpty() {
        MissionSearchCriteria missionSearchCriteria = new MissionSearchCriteria();
        missionSearchCriteria.setAuthorUsername("unknownUsername");

        List<Mission> result = objectToTest.searchAll(missionSearchCriteria);
        Assertions.assertThat(result).isEmpty();
    }

}
