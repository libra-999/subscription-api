package org.project.subscriptionservice.dao;

import org.project.subscriptionservice.bean.PaymentEntity;

public interface PaymentDao extends Dao<PaymentEntity> {

    PaymentEntity view(String ref);
}
