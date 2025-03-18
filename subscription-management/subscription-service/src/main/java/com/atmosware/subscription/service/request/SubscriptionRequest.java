package com.atmosware.subscription.service.request;

import lombok.Getter;

@Getter
public class SubscriptionRequest {
    private String userEmail;
    private String plan;

    public SubscriptionRequest(String mail, String plan) {
        this.userEmail = mail;
        this.plan = plan;
    }
}
