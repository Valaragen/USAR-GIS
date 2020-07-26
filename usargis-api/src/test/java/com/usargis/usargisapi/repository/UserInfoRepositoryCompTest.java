package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.UserInfo;
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
class UserInfoRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserInfoRepository objectToTest;

    private UserInfo sampleUser = UserInfoMother.sample().build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleUser);
    }

    @Test
    void findAll_shouldFindAllUsersInDb() {

        List<UserInfo> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindUserInDbById() {
        UserInfo userToFind = sampleUser;

        Optional<UserInfo> result = objectToTest.findById(sampleUser.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(userToFind);
    }

    @Test
    void save_shouldAddUserInDb() {
        UserInfo userInfoToSave = UserInfoMother.sample()
                .id("TEST_UUID")
                .username(("pepe"))
                .email("pepeTest@gmail.com")
                .build();

        objectToTest.save(userInfoToSave);

        Assertions.assertThat(entityManager.contains(userInfoToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        UserInfo userToDelete = sampleUser;

        objectToTest.delete(userToDelete);

        Assertions.assertThat(entityManager.contains(userToDelete)).isFalse();
    }

    @Test
    void findByUsername_shouldFindUserInDbByUsername() {
        UserInfo userToFind = sampleUser;

        Optional<UserInfo> result = objectToTest.findByUsername(userToFind.getUsername());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(userToFind);
    }

}
