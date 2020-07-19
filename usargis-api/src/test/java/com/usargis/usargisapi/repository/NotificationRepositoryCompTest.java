package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Notification;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.testutils.objectMother.model.NotificationMother;
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
class NotificationRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NotificationRepository objectToTest;

    private UserInfo sampleNotificationAuthor = UserInfoMother.sampleAuthor().build();
    private Notification sampleNotification = NotificationMother.sampleSent().author(sampleNotificationAuthor).build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleNotificationAuthor);
        entityManager.persist(sampleNotification);
    }

    @Test
    void findAll_shouldFindAllNotificationsInDb() {
        List<Notification> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindNotificationInDbById() {
        Notification notificationToFind = sampleNotification;

        Optional<Notification> result = objectToTest.findById(notificationToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(notificationToFind);
    }

    @Test
    void save_shouldAddNotificationInDb() {
        Notification notificationToSave = NotificationMother.sampleSent()
                .author(sampleNotificationAuthor)
                .build();

        objectToTest.save(notificationToSave);

        Assertions.assertThat(entityManager.contains(notificationToSave)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        Notification notificationToDelete = sampleNotification;

        objectToTest.delete(notificationToDelete);

        Assertions.assertThat(entityManager.contains(notificationToDelete)).isFalse();
    }

}
