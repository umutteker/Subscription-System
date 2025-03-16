package com.atmosware.notify.data.repository;

import com.atmosware.notify.data.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
