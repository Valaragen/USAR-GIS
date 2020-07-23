package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.*;
import com.usargis.usargisapi.util.objectMother.model.NotificationMessageMother;
import com.usargis.usargisapi.util.objectMother.model.NotificationMother;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
class NotificationMessageRepositoryCompTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NotificationMessageRepository objectToTest;

    private UserInfo sampleNotificationMessageAuthor = UserInfoMother.sampleAuthor().build();
    private Notification sampleNotification = NotificationMother.sampleSent().author(sampleNotificationMessageAuthor)
            .event(null).mission(null).build();
    private NotificationMessage sampleNotificationMessage = NotificationMessageMother.sample()
            .notification(sampleNotification)
            .contentType(NotificationMessageContentType.TEXT)
            .build();

    @BeforeEach
    void setup() {
        entityManager.persist(sampleNotificationMessageAuthor);
        entityManager.persist(sampleNotification);
        entityManager.persist(sampleNotificationMessage);
    }

    @Test
    void findAll_shouldFindAllNotificationMessagesInDb() {
        List<NotificationMessage> result = objectToTest.findAll();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldFindNotificationMessageInDbById() {
        NotificationMessage notificationMessageToFind = sampleNotificationMessage;

        Optional<NotificationMessage> result = objectToTest.findById(notificationMessageToFind.getId());

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo(notificationMessageToFind);
    }

    @Test
    void save_shouldAddNotificationMessageInDb() {
        NotificationMessage notificationMessageToSave = NotificationMessageMother.sample()
                .notification(sampleNotification)
                .contentType(NotificationMessageContentType.HTML)
                .build();

        objectToTest.save(notificationMessageToSave);

        Assertions.assertThat(entityManager.contains(notificationMessageToSave)).isTrue();
    }

    @Test
    void save_shouldAddCreateNotificationMessageSendingModeInDb() {
        NotificationMessage notificationMessageToSave = NotificationMessageMother.sample()
                .notification(sampleNotification)
                .contentType(NotificationMessageContentType.HTML)
                .sendingModes(new HashSet<>(Collections.singletonList(NotificationMessageSendingMode.MAIL)))
                .build();

        NotificationMessage result = objectToTest.save(notificationMessageToSave);

        Assertions.assertThat(result.getSendingModes().contains(NotificationMessageSendingMode.MAIL)).isTrue();
    }

    @Test
    void delete_shouldDeleteInDb() {
        NotificationMessage notificationMessageToDelete = sampleNotificationMessage;

        objectToTest.delete(notificationMessageToDelete);

        Assertions.assertThat(entityManager.contains(notificationMessageToDelete)).isFalse();
    }

}
