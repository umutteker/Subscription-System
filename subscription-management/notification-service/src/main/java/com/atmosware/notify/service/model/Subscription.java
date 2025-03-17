package com.atmosware.notify.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private Long id;
    private String userEmail;
    private String plan;
    private String status;
    private boolean active;
    private Date subscriptionDate;
    private Date nextPaymentDate;
}
