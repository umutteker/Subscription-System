package com.atmosware.payment.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class PaymentRequest implements Serializable {
    private Long subscriptionId;
    private double amount;
}
