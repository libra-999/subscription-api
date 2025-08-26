package org.project.subscriptionservice.dao.impl;

import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.dao.PaymentDao;
import org.project.subscriptionservice.dao.mapper.PaymentDaoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl extends DaoImpl<PaymentEntity, PaymentDaoMapper> implements PaymentDao {

}
