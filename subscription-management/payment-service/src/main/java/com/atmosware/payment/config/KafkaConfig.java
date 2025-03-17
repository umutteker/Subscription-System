package com.atmosware.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic paymentSuccessTopic() {
        return new NewTopic("payment_success", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentFaildeTopic() {
        return new NewTopic("payment_failed", 1, (short) 1);
    }
}
