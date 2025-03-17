package com.atmosware.notify.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

public interface NotificationService {
    @KafkaListener(topics = "payment_success", groupId = "notification-group")
    void sendPaymentSuccessNotification(String message) throws JsonProcessingException;

    @KafkaListener(topics = "payment_failed", groupId = "notification-group")
    void sendPaymentFailedNotification(String message) throws JsonProcessingException;
}
