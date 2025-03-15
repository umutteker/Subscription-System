package com.atmosware.notify.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentEvent {
    private Long subscriptionId;
    private String userEmail;  // SUCCESS veya FAILED
    private String plan;

    public PaymentEvent(Long subscriptionId, String userEmail, String plan) {
        this.subscriptionId = subscriptionId;
        this.plan = plan;
        this.userEmail = userEmail;
    }
}
