package com.atmosware.notify.service;

import org.springframework.kafka.annotation.KafkaListener;

public interface NotificationService {
    @KafkaListener(topics = "payment_success", groupId = "notification-group")
    void sendNotification(String message);
}
