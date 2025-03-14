package com.atmosware.subscription.api.controller;

import lombok.Getter;

@Getter
public class SubscriptionRequest {
    private String userEmail;
    private String plan;
}
