package com.atmosware.subscription.api.controller;

import com.atmosware.subscription.service.SubscriptionService;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
@Tag(name = "Create Subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<String> createSubscription(@RequestBody SubscriptionRequest request) throws JsonProcessingException {
        subscriptionService. createSubscription(request);
        return ResponseEntity.status(HttpStatus.OK).body("Aboneliğiniz oluşturuluyor");
    }
    @PutMapping("/cancel/{email}")
    public ResponseEntity<String> cancelSubscription(@PathVariable String email) throws JsonProcessingException {
        String response = subscriptionService.cancelSubscription(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/renew/{email}")
    public ResponseEntity<String> renewSubscription(@PathVariable String email) throws JsonProcessingException {
        String response = subscriptionService.renewSubscription(email);
        return ResponseEntity.ok(response);
    }
}
