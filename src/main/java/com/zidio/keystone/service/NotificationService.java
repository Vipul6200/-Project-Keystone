package com.zidio.keystone.service;
import com.zidio.keystone.entity.Notification;
import com.zidio.keystone.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public Notification createNotification(Long workOrderId, String message) {
        if (notificationRepository.existsByWorkOrderIdAndMessage(workOrderId, message)) {
            return notificationRepository.findByWorkOrderId(workOrderId).stream()
               .filter(n -> n.getMessage().equals(n.getMessage()))
               .findFirst()
               .orElse(null);
        }
        Notification notification = new Notification();
        notification.setWorkOrderId(workOrderId);
        notification.setMessage(message);
        notification.setCreatedAt(java.time.LocalDateTime.now());
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }
    public List<Notification> getNotifications(Long workOrderId) {
        return notificationRepository.findByWorkOrderId(workOrderId);
    }
}