package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.NotificationMessage;
import com.usargis.usargisapi.model.embeddable.NotificationMessageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, NotificationMessageId> {
}
