package com.atmosware.subscription.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name  = "Subscription")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_SUBSCRIPTION", sequenceName = "SEQ_SUBSCRIPTION")
@JsonIgnoreProperties(ignoreUnknown = true) // JSON parsing hatalarını önler
@ToString
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String plan;
    private String status;
    private boolean active;
    private Date subscriptionDate;
    private Date nextPaymentDate;
    @PrePersist
    protected void prePersist(){
        this.subscriptionDate = new Date();
    }
}
