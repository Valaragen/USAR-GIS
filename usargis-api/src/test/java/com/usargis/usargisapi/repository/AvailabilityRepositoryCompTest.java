package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilityCriteriaSortBy;
import com.usargis.usargisapi.core.search.AvailabilitySearchCriteria;
import com.usargis.usargisapi.util.objectMother.model.AvailabilityMother;
import com.usargis.usargisapi.util.objectMother.model.MissionMother;
import com.usargis.usargisapi.util.objectMother.model.UserInfoMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
class AvailabilityRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AvailabilityRepository objectToTest;

    private UserInfo sampleUser = UserInfoMother.sample().build();
    private UserInfo sampleMissionAuthor = UserInfoMother.sampleAuthor().build();
    private Mission sampleFinishedMission = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private Availability sampleAvailability = AvailabilityMother.sample()
            .userInfo(sampleUser).mission(sampleFinishedMission).build();

    //Adding more entities to test sorting
    private UserInfo sampleUser2 = UserInfoMother.sample2().build();
    private Mission sampleFinishedMission2 = MissionMother.sampleFinished().author(sampleMissionAuthor).build();
    private Availability sampleAvailability2 = AvailabilityMother.sample()
            .userInfo(sampleUser2).mission(sampleFinishedMission2).build();
    private Availability sampleAvailability3 = AvailabilityMother.sample()
            .userInfo(sampleUser).mission(sampleFinishedMission2).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleUser);
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleFinishedMission);
        entityManager.persist(sampleAvailability);

        entityManager.persist(sampleUser2);
        entityManager.persist(sampleFinishedMission2);
        entityManager.persist(sampleAvailability2);
        entityManager.persist(sampleAvailability3);
    }

    @Test
    void findAll_shouldFindAllAvailabilitiesInDb() {
        List<Availability> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void findById_shouldFindAvailabilityInDbById() {
        Availability availabilityToFind = sampleAvailability;

        Optional<Availability> result = objectToTest.findById(availabilityToFind.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result).contains(availabilityToFind);
    }

    @Test
    void save_shouldAddAvailabilityInDb() {
        Availability availabilityToSave = AvailabilityMother.sample()
                .mission(sampleFinishedMission)
                .userInfo(sampleUser)
                .build();

        objectToTest.save(availabilityToSave);

        Assertions.assertThat(entityManager.contains(availabilityToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Availability availabilityToDelete = sampleAvailability;

        objectToTest.delete(availabilityToDelete);

        Assertions.assertThat(entityManager.contains(availabilityToDelete)).isFalse();
    }

    @Test
    void searchAll_userUsernameGiven_shouldFindByUuid() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setUserUsername(sampleUser.getUsername());

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

    @Test
    void searchAll_missionIdGiven_shouldFindByMissionId() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(sampleFinishedMission.getId());

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

    @Test
    void searchAll_givenAvailabilitySearch_shouldFindAllGivenParameters() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(sampleFinishedMission.getId());
        availabilitySearchCriteria.setUserUsername(sampleUser.getUsername());

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

    @Test
    void searchAll_givenUnknownAvailabilitySearch_resultShouldBeEmpty() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(55555L);
        availabilitySearchCriteria.setUserUsername(sampleUser.getUsername());

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void searchAll_givenNoSortDirection_shouldSortDescByDefault() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(sampleFinishedMission2.getId());

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(2);
        Assertions.assertThat(result.get(0)).isEqualTo(sampleAvailability3);
        Assertions.assertThat(result.get(1)).isEqualTo(sampleAvailability2);
    }

    @Test
    void searchAll_givenSortDirectionAsc_shouldSortAsc() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(sampleFinishedMission2.getId());
        availabilitySearchCriteria.setOrder(Sort.Direction.ASC);

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(2);
        Assertions.assertThat(result.get(0)).isEqualTo(sampleAvailability2);
        Assertions.assertThat(result.get(1)).isEqualTo(sampleAvailability3);
    }

    @Test
    void searchAll_givenSortByUsernameAsc_shouldSortByUsernameAsc() {
        AvailabilitySearchCriteria availabilitySearchCriteria = new AvailabilitySearchCriteria();
        availabilitySearchCriteria.setMissionId(sampleFinishedMission2.getId());
        availabilitySearchCriteria.setSortBy(AvailabilityCriteriaSortBy.USER_USERNAME);
        availabilitySearchCriteria.setOrder(Sort.Direction.ASC);

        List<Availability> result = objectToTest.searchAll(availabilitySearchCriteria);

        Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(2);
        Assertions.assertThat(result.get(0)).isEqualTo(sampleAvailability3);
        Assertions.assertThat(result.get(1)).isEqualTo(sampleAvailability2);
    }
}
