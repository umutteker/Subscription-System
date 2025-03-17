package com.atmosware.subscription.service.impl;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.service.PaymentScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentSchedulerImpl implements PaymentScheduler {
    private final SubscriptionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Scheduled(cron = "0 0 0 * * ?") // Her gün gece yarısı çalışır
    public void processDailyPayments() throws JsonProcessingException {
        List<Subscription> dueSubscriptions = repository.findByActiveTrueAndNextPaymentDate(new Date());

        for (Subscription subscription : dueSubscriptions) {
            String subscriptionJson = objectMapper.writeValueAsString(subscription);
            kafkaTemplate.send("payment_request", subscriptionJson);
            System.out.println("📌 Günlük ödeme isteği gönderildi: " + subscription.getUserEmail());
        }
    }
}
