package com.atmosware.subscription.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private String userId;
    private String status;  // SUCCESS veya FAILED
    private double amount;
}
