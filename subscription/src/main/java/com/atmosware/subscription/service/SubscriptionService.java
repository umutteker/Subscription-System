package com.atmosware.subscription.service;

import com.atmosware.subscription.data.entity.Subscription;
import org.apache.coyote.BadRequestException;

public interface SubscriptionService {
    Subscription createSubscription(Subscription subscription);

    void activateSubscription(Long id) throws BadRequestException;
}
