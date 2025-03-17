package com.atmosware.payment.service.impl;

import com.atmosware.payment.data.entity.Payment;
import com.atmosware.payment.data.repository.PaymentRepository;
import com.atmosware.payment.service.PaymentService;
import com.atmosware.payment.service.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final PaymentRepository repository;

    @KafkaListener(topics = "payment_request", groupId = "payment-group")
    @Override
    public void processPayment(String subscriptionJson) throws JsonProcessingException {
        BigDecimal amount = new BigDecimal(30);
        System.out.println("Processing payment: " + subscriptionJson);
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);
        Payment payment = new Payment(subscription.getUserEmail(), subscription.getId(), amount);
        boolean success = Math.random() > 0.3; // %70 başarılı ödeme simülasyonu
        System.out.println("Payment Status: " + success);
        if (success) {
            payment.setStatus("SUCCESS");
            kafkaTemplate.send("payment_success", subscriptionJson);
        } else {
            payment.setStatus("FAILED");
            kafkaTemplate.send("payment_failed", subscriptionJson);
        }
        repository.save(payment);
    }

    @KafkaListener(topics = "subscription_cancellation", groupId = "payment-group")
    public void handleSubscriptionCancellation(String subscriptionJson) throws JsonProcessingException {
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);

        System.out.println("📌 Kullanıcı aboneliği iptal edildi, ödeme durdurulacak: " + subscription.getUserEmail());
    }
}
