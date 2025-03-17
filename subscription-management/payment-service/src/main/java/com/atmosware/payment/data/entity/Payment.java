package com.atmosware.payment.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name  = "Payment")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_PAYMENT", sequenceName = "SEQ_PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long subscriptionId;
    private String userEmail;
    private String status;
    private BigDecimal amount;
    private Date paymentDate;

    public Payment(String userEmail, Long subscriptionId, BigDecimal amount) {
        this.userEmail = userEmail;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
    }

    @PrePersist
    protected void prePersist(){
        this.paymentDate = new Date();
    }
}
