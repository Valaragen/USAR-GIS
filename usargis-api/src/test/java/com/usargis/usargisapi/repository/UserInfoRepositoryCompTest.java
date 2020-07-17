package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.testutils.objectMother.UserInfoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
public class UserInfoRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(UserInfoMother.complete().build());
        entityManager.flush();
    }

    @Test
    void save_shouldAddUserInDb() {

    }

//    @Test
//    void delete_shouldDelete

}
