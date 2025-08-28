package org.project.subscriptionservice.domain.service;

import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.share.entity.MetaData;

public interface PaymentService {

    PaymentEntity verify(String code);

    PaymentEntity view(Integer id, MetaData metaData);
}
