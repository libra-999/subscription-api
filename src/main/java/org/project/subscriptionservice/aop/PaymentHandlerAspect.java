package org.project.subscriptionservice.aop;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.project.subscriptionservice.bean.InvoiceEntity;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionMemberEntity;
import org.project.subscriptionservice.domain.service.RedisService;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentHandlerAspect {

    private final RedisService redisService;

    @Pointcut(value = "@annotation(org.project.subscriptionservice.aop.PaymentHandler)")
    public void handle() {
    }

    @SneakyThrows
    @AfterReturning(value = "handle() && args(entity, ..)", returning = "result", argNames = "entity,result")
    public void handleAddressPayment(PaymentEntity entity, SubscriptionEntity result) {

        String username = "";
        String city = "";
        String street = "";
        String country = "";

        for (SubscriptionMemberEntity member : result.getMembers()) {
            username = member.getUserEntity().getUsername();
        }

        for (InvoiceEntity invoice : result.getInvoices()) {
            for (PaymentEntity payment : invoice.getPayments()) {
                city = payment.getCity();
                street = payment.getStreet();
                country = payment.getCountry();
            }
        }

        String json = username + "," + city + "," + street + "," + country;
        redisService.setValue(result.getSubscriptionPlan().getPlanRef(),
            json, 5L, TimeUnit.DAYS);
        log.info("::: User Payment = {},{},{}, {} :::",
            username, street, city, country);
    }

}
