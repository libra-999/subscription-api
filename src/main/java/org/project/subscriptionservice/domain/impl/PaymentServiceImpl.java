package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.aop.MetaHandler;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.bean.enums.PaymentStatus;
import org.project.subscriptionservice.dao.PaymentDao;
import org.project.subscriptionservice.dao.UserDao;
import org.project.subscriptionservice.domain.exception.PaymentException;
import org.project.subscriptionservice.domain.service.PaymentService;
import org.project.subscriptionservice.share.entity.MetaData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDao paymentDao;
    private final UserDao userDao;
    private final KafkaTemplate<String , Object> kfkTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentEntity verify(String code) {

        int length = code.indexOf("_");
        String ref = code.substring(0, length);
        PaymentEntity payment = paymentDao.view(ref);
        try {
            payment.setStatus(PaymentStatus.SUCCESS);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            throw PaymentException.paymentFailed();
        }

        paymentDao.update(payment, payment.getId());
        kfkTemplate.send("auto-subscribe", payment.getReference(), payment);
        return payment;
    }

    @Override
    @Transactional(readOnly = true)
    @MetaHandler
    public PaymentEntity view(Integer id, MetaData metaData) {
        PaymentEntity payment = paymentDao.view(id);
        if (payment == null) {
            throw PaymentException.notFound();
        }
        UserEntity user = userDao.view(payment.getUserId(), metaData.getUsername());
        payment.setUser(user);
        return payment;
    }
}
