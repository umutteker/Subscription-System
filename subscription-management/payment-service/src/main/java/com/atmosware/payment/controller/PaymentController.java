package com.atmosware.payment.controller;

import com.atmosware.payment.service.PaymentService;
import com.atmosware.payment.service.request.PaymentRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/process")
    public String processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request.getSubscriptionId().toString());
    }
}
