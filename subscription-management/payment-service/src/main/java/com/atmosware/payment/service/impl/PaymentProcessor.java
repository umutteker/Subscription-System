package com.atmosware.payment.service.impl;

import com.atmosware.payment.data.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {
    public boolean process(Payment payment) {
        return Math.random() > 0.3; // %70 başarılı ödeme simülasyonu
    }
}
