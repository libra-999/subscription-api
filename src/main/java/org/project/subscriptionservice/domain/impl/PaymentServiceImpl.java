package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.domain.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentEntity verify(String paymentNo, String uuid) {
        return null;
    }
}
