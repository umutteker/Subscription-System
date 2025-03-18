package com.atmosware.subscription.repository;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.data.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SubscriptionRepositoryTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldSaveAndFindSubscription() {
        Subscription subscription = new Subscription();
        subscription.setUserEmail("test@example.com");
        subscription.setActive(true);
        subscription.setStatus("ACTIVE");

        subscriptionRepository.save(subscription);

        Optional<Subscription> found = subscriptionRepository.findByUserEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getUserEmail());
    }
}
