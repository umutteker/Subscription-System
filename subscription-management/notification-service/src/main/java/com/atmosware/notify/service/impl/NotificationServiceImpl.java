package com.atmosware.notify.service.impl;

import com.atmosware.notify.service.NotificationService;
import com.atmosware.notify.service.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment_success", groupId = "notification-group")
    @Override
    public void sendPaymentSuccessNotification(String subscriptionJson) throws JsonProcessingException {
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);
        System.out.println("Sending Success notification: " + subscription.getId());
    }

    @KafkaListener(topics = "payment_failed", groupId = "notification-group")
    @Override
    public void sendPaymentFailedNotification(String subscriptionJson) throws JsonProcessingException {
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);
        System.out.println("Sending Fail notification: " + subscription.getId());
    }
}