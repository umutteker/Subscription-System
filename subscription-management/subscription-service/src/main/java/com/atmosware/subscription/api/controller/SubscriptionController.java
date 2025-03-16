package com.atmosware.subscription.api.controller;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.service.SubscriptionService;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
@Tag(name = "Create Subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    private KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    @PostMapping
    public Subscription createSubscription(@RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }

    @GetMapping("/{id}")
    public Subscription getSubscription(@PathVariable Long id) {
        return subscriptionService.getSubscription(id);
    }
}
