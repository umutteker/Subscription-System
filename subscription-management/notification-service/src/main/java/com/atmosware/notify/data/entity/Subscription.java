package com.atmosware.notify.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String userId;
    private String status; // ACTIVE, CANCELED
}
