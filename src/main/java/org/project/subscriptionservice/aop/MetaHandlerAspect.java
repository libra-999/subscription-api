package org.project.subscriptionservice.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.project.subscriptionservice.config.jwt.Util;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MetaHandlerAspect {

    private final Util util;
    private final HttpServletRequest request;

    @Pointcut(value = "@annotation(org.project.subscriptionservice.aop.MetaHandler)")
    public void handle() {
    }

    @SneakyThrows
    @Before(value = "handle() && args(.., metaData) , throwing = e")
    public void action(JoinPoint joinPoint, MetaData metaData) {

        String token = request.getHeader("Authorization");
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = util.getUserNameFromJwtToken(token);

            metaData.setUsername(username);
        } else {
            throw new HttpException(HttpStatus.UNAUTHORIZED,"X-User-ID is invalid");
        }
    }
}
