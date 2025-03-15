package com.atmosware.payment.service.impl;

import com.atmosware.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "payment-events", groupId = "subscription-group")
    public void processPayment(String event) {
        boolean success = Math.random() > 0.2; // %80 başarılı ödeme simülasyonu

        if (success) {
            kafkaTemplate.send("subscription-activation-topic", event);
        }
    }
}
