package com.atmosware.subscription.service;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.service.request.SubscriptionRequest;

public interface SubscriptionService {
    Subscription createSubscription(SubscriptionRequest request);

    Subscription getSubscription(Long id);
}
