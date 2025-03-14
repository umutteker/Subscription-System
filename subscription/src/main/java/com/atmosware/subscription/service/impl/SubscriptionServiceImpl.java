package com.atmosware.subscription.service.impl;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;

    @Override
    public Subscription createSubscription(Subscription subscription){
        subscription.setActive(false);
        return repository.save(subscription);
    }

    @Override
    public void activateSubscription(Long id) throws BadRequestException {
        Subscription subscription = repository.findById(id).orElseThrow(BadRequestException::new);
        subscription.setActive(true);
        repository.save(subscription);
    }
}
