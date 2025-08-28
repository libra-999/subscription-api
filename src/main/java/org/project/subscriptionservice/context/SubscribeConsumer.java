package org.project.subscriptionservice.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.bean.enums.PaymentStatus;
import org.project.subscriptionservice.dao.PaymentDao;
import org.project.subscriptionservice.dao.UserDao;
import org.project.subscriptionservice.domain.exception.PaymentException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeConsumer {
    private final PushEmailService pushEmailService;
    private final UserDao userDao;
    private final PaymentDao paymentDao;
    private final KafkaTemplate<String , Object> kfkTemplate;

    @KafkaListener(topics = "auto-subscribe", groupId = "Subscribe", concurrency = "10")
    public PaymentEntity consumePayment(PaymentEntity payment) {
        UserEntity user = payment.getUser();

        switch (payment.getStatus()) {
            case SUCCESS: {
                BigDecimal total = user.getBalance().subtract(payment.getAmount());

                user.setBalance(total);
                payment.setUser(user);

                paymentDao.update(payment, payment.getId());
                userDao.update(user, user.getId());
                pushEmailService.sendMessage(user.getEmail(), "Payment Successfully", "Payment");
                pushEmailService.sendMessage(user.getEmail(), "You plan have been renew subscribe", "Subscribe");
                break;
            }

            case FAILED: {
                payment.setStatus(PaymentStatus.FAILED);
                pushEmailService.sendMessage(user.getEmail(), "Payment Failed", "Payment");
                break;
            }
            case PENDING: {
                payment.setStatus(PaymentStatus.SUCCESS);
                paymentDao.update(payment, payment.getId());
                kfkTemplate.send("auto-subscribe", payment.getReference(), payment);
                break;
            }
            default:
                throw PaymentException.paymentFailed();
        }
        return payment;
    }
}
