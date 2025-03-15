package com.atmosware.subscription.event;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentEvent {
    private Long id;
    private String userEmail;  // SUCCESS veya FAILED
    private String plan;

    public PaymentEvent(Long id, String userEmail, String plan) {
        this.id = id;
        this.plan = plan;
        this.userEmail = userEmail;
    }
}
