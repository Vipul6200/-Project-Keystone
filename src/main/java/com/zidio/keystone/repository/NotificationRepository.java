package com.zidio.keystone.repository;
import com.zidio.keystone.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByWorkOrderId(Long workOrderId);
    boolean existsByWorkOrderIdAndMessage(Long workOrderId, String message);
}
