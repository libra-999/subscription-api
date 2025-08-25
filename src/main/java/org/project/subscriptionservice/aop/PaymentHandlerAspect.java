package org.project.subscriptionservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PaymentHandlerAspect {

    @Pointcut(value = "@annotation(org.project.subscriptionservice.aop.PaymentHandler)")
    public void handle(){}

}
