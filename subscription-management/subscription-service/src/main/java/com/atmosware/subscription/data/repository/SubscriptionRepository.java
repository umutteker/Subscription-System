package com.atmosware.subscription.data.repository;

import com.atmosware.subscription.data.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserEmail(String userEmail);

    List<Subscription> findByActiveTrueAndNextPaymentDate(Date today);
}
