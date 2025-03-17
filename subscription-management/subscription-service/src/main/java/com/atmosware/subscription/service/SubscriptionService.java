package com.atmosware.subscription.service;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

public interface SubscriptionService {
    Subscription createSubscription(SubscriptionRequest request) throws JsonProcessingException;

    @KafkaListener(topics = "payment_success", groupId = "notification-group")
    void activateSubscription(String subscriptionJson) throws JsonProcessingException;

    void deactivateSubscription(String subscriptionJson) throws JsonProcessingException;

    String cancelSubscription(String email) throws JsonProcessingException;

    String renewSubscription(String email) throws JsonProcessingException;
}
