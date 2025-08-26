package org.project.subscriptionservice.domain.service;

import org.project.subscriptionservice.bean.PaymentEntity;

public interface PaymentService {

    PaymentEntity verify(String paymentNo, String uuid);



}
