package com.atmosware.payment.service.impl;

import com.atmosware.payment.service.PaymentService;
import com.atmosware.payment.service.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "payment_request", groupId = "payment-group")
    @Override
    public String processPayment(String paymentRequest) {
        System.out.println("Processing payment: " + paymentRequest);
        kafkaTemplate.send("payment_success", "Payment successful: " + paymentRequest);
        return paymentRequest;
    }
}
