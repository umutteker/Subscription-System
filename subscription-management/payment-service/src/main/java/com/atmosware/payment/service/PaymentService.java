package com.atmosware.payment.service;

import com.atmosware.payment.service.request.PaymentRequest;
import org.springframework.kafka.annotation.KafkaListener;

public interface PaymentService {

    @KafkaListener(topics = "payment_request", groupId = "payment-group")
    String processPayment(String message);
}
