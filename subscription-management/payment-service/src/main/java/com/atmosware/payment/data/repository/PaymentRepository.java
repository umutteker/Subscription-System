package com.atmosware.payment.data.repository;

import com.atmosware.payment.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
