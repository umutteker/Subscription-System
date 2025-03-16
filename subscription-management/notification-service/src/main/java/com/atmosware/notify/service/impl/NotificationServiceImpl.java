package com.atmosware.notify.service.impl;

import com.atmosware.notify.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @KafkaListener(topics = "payment_success", groupId = "notification-group")
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending notification: " + message);
    }
}
