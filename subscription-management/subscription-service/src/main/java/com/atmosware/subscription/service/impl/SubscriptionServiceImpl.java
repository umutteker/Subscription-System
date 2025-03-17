package com.atmosware.subscription.service.impl;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.service.SubscriptionService;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Subscription createSubscription(SubscriptionRequest request) throws JsonProcessingException {
        Subscription subscription = new Subscription();
        subscription.setUserEmail(request.getUserEmail());
        subscription.setPlan(request.getPlan());
        subscription.setStatus("PENDING");
        subscription.setActive(false);
        Subscription saved = repository.save(subscription);
        String subscriptionJson = objectMapper.writeValueAsString(saved);
        kafkaTemplate.send("payment_request", subscriptionJson);
        return saved;
    }

    @KafkaListener(topics = "payment_success", groupId = "payment-group")
    @Override
    public void activateSubscription(String subscriptionJson) throws JsonProcessingException {
        System.out.println("activateSubscription: " + subscriptionJson);
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);
        subscription.setNextPaymentDate(extendOneMonthofGivenDate(new Date()));
        subscription.setStatus("ACTIVE");
        subscription.setActive(true);
        repository.save(subscription);
    }


    @KafkaListener(topics = "payment_failed", groupId = "payment-group")
    @Override
    public void deactivateSubscription(String subscriptionJson) throws JsonProcessingException {
        System.out.println("deactivateSubscription: " + subscriptionJson);
        Subscription subscription = objectMapper.readValue(subscriptionJson, Subscription.class);
        subscription.setStatus("PASSIVE");
        subscription.setActive(false);
        repository.save(subscription);
    }

    @Override
    public String cancelSubscription(String email) throws JsonProcessingException {
        Optional<Subscription> optionalSubscription = repository.findByUserEmail(email).stream().findFirst();
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            subscription.setStatus("CANCELLED");
            subscription.setActive(false);
            subscription.setNextPaymentDate(null);
            repository.save(subscription);

            String subscriptionJson = objectMapper.writeValueAsString(subscription);
            kafkaTemplate.send("subscription_cancellation", subscriptionJson);
            return "Subscription cancelled successfully.";
        }
        return "Subscription not found.";
    }

    @Override
    public String renewSubscription(String email) throws JsonProcessingException {
        Optional<Subscription> optionalSubscription = repository.findByUserEmail(email).stream().findFirst();
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            if (subscription.getStatus().equals("CANCELLED") || subscription.getStatus().equals("PASSIVE")) {
                subscription.setStatus("RENEW");
                repository.save(subscription);

                String subscriptionJson = objectMapper.writeValueAsString(subscription);
                kafkaTemplate.send("payment_request", subscriptionJson);
                return "Subscription renewed successfully.";
            }
            return "Subscription is already active.";
        }
        return "Subscription not found.";
    }

    private static Date extendOneMonthofGivenDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
}
