package com.atmosware.subscription.api.controller;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long subscriptionId;
    private double amount;
}
