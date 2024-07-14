package com.danslans.alertbuyers.data.repositories;

import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByEmail(String email);
}
