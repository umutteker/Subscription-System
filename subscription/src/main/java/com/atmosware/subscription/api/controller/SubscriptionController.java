package com.atmosware.subscription.api.controller;

import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
@Tag(name = "Create Subscription")
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    public Subscription createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription(subscriptionRequest.getUserEmail(),subscriptionRequest.getPlan());
        return service.createSubscription(subscription);
    }

    @PostMapping("/activate/{id}")
    public void activateSubscription(@PathVariable Long id) throws BadRequestException {
        service.activateSubscription(id);
    }
}
