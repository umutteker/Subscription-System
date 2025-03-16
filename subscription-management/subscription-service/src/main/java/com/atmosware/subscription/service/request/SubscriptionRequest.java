package com.atmosware.subscription.service.request;

import lombok.Getter;

@Getter
public class SubscriptionRequest {
    private String userEmail;
    private String plan;
}
