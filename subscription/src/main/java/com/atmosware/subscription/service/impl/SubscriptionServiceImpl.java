package com.atmosware.subscription.service.impl;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.event.PaymentEvent;
import com.atmosware.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Override
    public Subscription createSubscription(Subscription subscription){
        subscription.setActive(false);
        Subscription saved = repository.save(subscription);
        kafkaTemplate.send("payment-events", new PaymentEvent(saved.getId(), saved.getUserEmail(), saved.getPlan()));
        return saved;
    }

    @Override
    public void activateSubscription(Long id) throws BadRequestException {
        Subscription subscription = repository.findById(id).orElseThrow(BadRequestException::new);
        subscription.setActive(true);
        repository.save(subscription);
    }
}
