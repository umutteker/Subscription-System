package com.atmosware.subscription.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name  = "Subscription")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_SUBSCRIPTION", sequenceName = "SEQ_SUBSCRIPTION")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String plan;
    private boolean isActive;
    private Date subscriptionDate;

    public Subscription(String userEmail, String plan) {
        this.userEmail = userEmail;
        this.plan = plan;
    }

    @PrePersist
    protected void prePersist(){
        this.subscriptionDate = new Date();
    }
}
