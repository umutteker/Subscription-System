package com.atmosware.notify.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic paymentRequestTopic() {
        return new NewTopic("payment_request", 1, (short) 1);
    }
    public NewTopic cancelSubscriptionTopic() {
        return new NewTopic("subscription_cancellation", 1, (short) 1);
    }

}
