package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilitySearch;
import com.usargis.usargisapi.testutils.objectMother.model.AvailabilityMother;
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

    @BeforeEach
    void setup() {
        entityManager.persist(sampleUser);
        entityManager.persist(sampleMissionAuthor);
        entityManager.persist(sampleFinishedMission);
        entityManager.persist(sampleAvailability);
    }

    @Test
    void findAll_shouldFindAllAvailabilitiesInDb() {
        List<Availability> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
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
    void searchAll_userUuidGiven_shouldFindByUuid() {
        AvailabilitySearch availabilitySearch = new AvailabilitySearch();
        availabilitySearch.setUserId(sampleUser.getId());

        List<Availability> result = objectToTest.searchAll(availabilitySearch);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

    @Test
    void searchAll_missionIdGiven_shouldFindByMissionId() {
        AvailabilitySearch availabilitySearch = new AvailabilitySearch();
        availabilitySearch.setMissionId(sampleFinishedMission.getId());

        List<Availability> result = objectToTest.searchAll(availabilitySearch);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

    @Test
    void searchAll_givenAvailabilitySearch_shouldFindAllGivenParameters() {
        AvailabilitySearch availabilitySearch = new AvailabilitySearch();
        availabilitySearch.setMissionId(sampleFinishedMission.getId());
        availabilitySearch.setUserId(sampleUser.getId());

        List<Availability> result = objectToTest.searchAll(availabilitySearch);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.contains(sampleAvailability)).isTrue();
    }

}
