package com.atmosware.notify.service;

import com.atmosware.notify.service.impl.NotificationServiceImpl;
import com.atmosware.notify.service.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(partitions = 1, topics = {"payment_success", "payment_failed"})
public class NotificationServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private ObjectMapper objectMapper;

    private String subscriptionJson;

    @BeforeEach
    void setUp() {
        subscriptionJson = "{\"userEmail\":\"test@example.com\",\"plan\":\"PREMIUM\",\"status\":\"PENDING\",\"active\":false}";
    }

    @Test
    void shouldSendPaymentSuccessNotification() throws JsonProcessingException {
        // Given
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUserEmail("test@example.com");
        when(objectMapper.readValue(subscriptionJson, Subscription.class)).thenReturn(subscription);

        notificationService.sendPaymentSuccessNotification(subscriptionJson);

        System.out.println("Expected Success Notification Sent for Subscription ID: " + subscription.getId());
    }

    @Test
    void shouldSendPaymentFailedNotification() throws JsonProcessingException {
        Subscription subscription = new Subscription();
        subscription.setId(2L);
        subscription.setUserEmail("fail@example.com");

        when(objectMapper.readValue(subscriptionJson, Subscription.class)).thenReturn(subscription);

        notificationService.sendPaymentFailedNotification(subscriptionJson);

        System.out.println("Expected Fail Notification Sent for Subscription ID: " + subscription.getId());
    }
}
