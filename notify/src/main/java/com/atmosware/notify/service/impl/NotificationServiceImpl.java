package com.atmosware.notify.service.impl;

import com.atmosware.notify.event.PaymentEvent;
import com.atmosware.notify.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @KafkaListener(topics = "subscription-activation-topic", groupId = "group-id")
    public void sendActivationEmail(PaymentEvent event) {
        System.out.println("E-mail sent to: " + event.getUserEmail() + " for subscription: " + event.getPlan());
    }
}
