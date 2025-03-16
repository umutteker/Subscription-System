package com.atmosware.payment.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private Long subscriptionId;
    private String userEmail;
    private String plan;
    private String status; // SUCCESS veya FAILED
    private BigDecimal amount;
}
