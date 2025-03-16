package com.atmosware.subscription.service.impl;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.service.SubscriptionService;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Subscription createSubscription(SubscriptionRequest request) {
        Subscription subscription = new Subscription();
        subscription.setUserEmail(request.getUserEmail());
        subscription.setPlan(request.getPlan());
        subscription.setActive(false);
        Subscription saved = repository.save(subscription);
        kafkaTemplate.send("payment_request", "Payment request for subscription: " + saved.getId());
        return saved;
    }

    @Override
    public Subscription getSubscription(Long id) {
        return repository.findById(id).orElse(null);
    }
}
