package com.atmosware.payment.service;

import com.atmosware.payment.data.entity.Payment;
import com.atmosware.payment.data.repository.PaymentRepository;
import com.atmosware.payment.service.impl.PaymentProcessor;
import com.atmosware.payment.service.impl.PaymentServiceImpl;
import com.atmosware.payment.service.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private PaymentRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PaymentProcessor paymentProcessor;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Subscription subscription;
    private String subscriptionJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        subscription = new Subscription();
        subscription.setUserEmail("test@example.com");
        subscription.setId(1L);

        subscriptionJson = "{\"userEmail\":\"test@example.com\", \"id\":1}";

        when(objectMapper.readValue(subscriptionJson, Subscription.class)).thenReturn(subscription);
    }

    @Test
    void shouldProcessPaymentFailure() throws JsonProcessingException {
        String subscriptionJson = "{\"userEmail\":\"test@example.com\", \"id\":1}";

        when(paymentProcessor.process(any(Payment.class))).thenReturn(false);

        paymentService.processPayment(subscriptionJson);

        verify(kafkaTemplate, times(1)).send(eq("payment_failed"), eq(subscriptionJson));
        verify(kafkaTemplate, never()).send(eq("payment_success"), anyString());
        verify(repository, times(1)).save(any(Payment.class));
    }

    @Test
    void shouldProcessPaymentSuccess() throws JsonProcessingException {
        String subscriptionJson = "{\"userEmail\":\"test@example.com\", \"id\":1}";

        when(paymentProcessor.process(any(Payment.class))).thenReturn(true);

        paymentService.processPayment(subscriptionJson);

        verify(kafkaTemplate, times(1)).send(eq("payment_success"), eq(subscriptionJson));
        verify(kafkaTemplate, never()).send(eq("payment_failed"), anyString());
        verify(repository, times(1)).save(any(Payment.class));
    }
}
