package com.atmosware.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

public interface PaymentService {

    @KafkaListener(topics = "payment_request", groupId = "payment-group")
    void processPayment(String message) throws JsonProcessingException;
}
