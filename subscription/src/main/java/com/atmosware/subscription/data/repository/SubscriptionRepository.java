package com.atmosware.subscription.data.repository;

import com.atmosware.subscription.data.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
