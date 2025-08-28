package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.bean.*;
import org.project.subscriptionservice.bean.enums.AccountStatus;
import org.project.subscriptionservice.bean.enums.InvoiceStatus;
import org.project.subscriptionservice.bean.enums.PaymentStatus;
import org.project.subscriptionservice.context.PushEmailService;
import org.project.subscriptionservice.dao.*;
import org.project.subscriptionservice.domain.exception.InvoiceException;
import org.project.subscriptionservice.domain.exception.SubException;
import org.project.subscriptionservice.domain.service.InvoiceService;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static org.project.subscriptionservice.domain.impl.SubServiceImpl.generateInvoiceNumber;
import static org.project.subscriptionservice.domain.impl.SubServiceImpl.generateRefer;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final SubPlanDao subPlanDao;
    private final InvoiceDao invoiceDao;
    private final UserDao userDao;
    private final SubDao subDao;
    private final PaymentDao paymentDao;
    private final PushEmailService pushEmailService;
        private final KafkaTemplate<String , Object> kfkTemplate;


    @Deprecated
    @Override
    @Transactional(rollbackFor = HttpException.class)
    public InvoiceEntity create(Integer userId, Integer subId, SubscriptionPlanEntity subPlan) {
        SubscriptionEntity sub = subDao.view(subId);
        UserEntity user = userDao.view(userId);

        InvoiceEntity invoiceEntity = new InvoiceEntity();
        if (sub.getAutoRenew() == true){

            invoiceEntity.setInvoiceNo(generateInvoiceNumber(subId));
            invoiceEntity.setStatus(InvoiceStatus.PENDING);
            invoiceEntity.setCreatedAt(new Date());
            invoiceEntity.setCreatedBy(user.getUsername());
            invoiceEntity.setCurrency(subPlan.getCurrency());
            invoiceEntity.setAmount(subPlan.getPrice());
            invoiceEntity.setDueDate(sub.getPlanEnd());
            invoiceEntity.setSubscriptionId(subId);

            invoiceDao.create(invoiceEntity);
            verifyPayment(invoiceEntity, user);
        }else {
            throw new SubException(HttpStatus.BAD_REQUEST, "You dont have auto renew");
        }
        return invoiceEntity;
    }

    @Override
    public InvoiceEntity view(Integer id) {
        InvoiceEntity invoiceEntity = invoiceDao.view(id);
        if (invoiceEntity == null) {
            throw InvoiceException.notFound();
        }

        SubscriptionEntity sub = subDao.view(invoiceEntity.getSubscriptionId());
        SubscriptionPlanEntity planEntity = subPlanDao.view(sub.getPlanId());
        invoiceEntity.setSubscription(sub);
        sub.setSubscriptionPlan(planEntity);
        return invoiceEntity;
    }

    @Override
    public InvoiceEntity generateInvoice() {
        return null;
    }


    @Transactional(rollbackFor = HttpException.class)
    public void verifyPayment(InvoiceEntity invoice, UserEntity user) {

        PaymentEntity paymentEntity = new PaymentEntity();
        if (Objects.equals(user.getActive(), AccountStatus.ACTIVE) && user.getLocked() == 0  ) {
            Integer invoiceId = invoice.getId();

            paymentEntity.setInvoiceId(invoiceId);
            paymentEntity.setUserId(user.getId());
            paymentEntity.setReference(generateRefer(invoiceId));
            paymentEntity.setAmount(invoice.getAmount());
            paymentEntity.setCurrency(invoice.getCurrency());
            paymentEntity.setPaymentDate(LocalDateTime.now());
            paymentEntity.setStatus(PaymentStatus.PENDING);

            for (PaymentEntity payment : invoice.getPayments()) {
                paymentEntity.setPaymentMethod(payment.getPaymentMethod());
                paymentEntity.setState(payment.getState());
                paymentEntity.setCity(payment.getCity());
                paymentEntity.setCountry(payment.getCountry());
                paymentEntity.setZipCode(payment.getZipCode());
            }

            paymentEntity.setCreatedAt(new Date());
            paymentEntity.setCreatedBy(user.getUsername());
            paymentDao.create(paymentEntity);

            pushEmailService.sendMessage(user.getEmail(), "Auto Renew", "Subscribe");
            kfkTemplate.send("auto-subscribe", paymentEntity.getReference(), paymentEntity);
        }
    }
}
