package com.atmosware.subscription.service;

import com.atmosware.subscription.data.repository.SubscriptionRepository;
import com.atmosware.subscription.service.impl.SubscriptionServiceImpl;
import com.atmosware.subscription.data.entity.Subscription;
import com.atmosware.subscription.service.request.SubscriptionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private SubscriptionRequest request;
    private Subscription subscription;
    private String subscriptionJson;

    @BeforeEach
    void setUp() {
        request = new SubscriptionRequest("test@example.com","PREMIUM");

        subscriptionJson = "{\"userEmail\":\"test@example.com\",\"plan\":\"PREMIUM\",\"status\":\"PENDING\",\"active\":false}";

        subscription = new Subscription();
        subscription.setUserEmail("test@example.com");
        subscription.setActive(true);
        subscription.setStatus("ACTIVE");
    }

    @Test
    void shouldCancelSubscription() throws JsonProcessingException {
        when(subscriptionRepository.findByUserEmail("test@example.com")).thenReturn(Optional.of(subscription));
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        String result = subscriptionService.cancelSubscription("test@example.com");

        assertEquals("Subscription cancelled successfully.", result);
        assertFalse(subscription.isActive());
        assertEquals("CANCELLED", subscription.getStatus());
        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
    }

    @Test
    void shouldCreateSubscriptionAndSendKafkaMessage() throws JsonProcessingException {

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(objectMapper.writeValueAsString(any(Subscription.class))).thenReturn(subscriptionJson);

        Subscription savedSubscription = subscriptionService.createSubscription(request);

        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
        assertTrue(savedSubscription.isActive());

        verify(kafkaTemplate, times(1)).send("payment_request", subscriptionJson);
    }

    @Test
    void shouldActivateSubscriptionOnPaymentSuccess() throws JsonProcessingException {
        String subscriptionJson = "{\"userEmail\":\"test@example.com\", \"status\":\"PENDING\", \"active\":false}";
        when(objectMapper.readValue(subscriptionJson, Subscription.class)).thenReturn(subscription);

        subscriptionService.activateSubscription(subscriptionJson);

        assertEquals("ACTIVE", subscription.getStatus());
        assertTrue(subscription.isActive());
        assertNotNull(subscription.getNextPaymentDate());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void shouldDeactivateSubscriptionOnPaymentFailed() throws JsonProcessingException {
        String subscriptionJson = "{\"userEmail\":\"test@example.com\", \"status\":\"PENDING\", \"active\":false}";
        when(objectMapper.readValue(subscriptionJson, Subscription.class)).thenReturn(subscription);
        
        subscriptionService.deactivateSubscription(subscriptionJson);

        assertEquals("PASSIVE", subscription.getStatus());
        assertFalse(subscription.isActive());

        verify(subscriptionRepository, times(1)).save(subscription);
    }
}
