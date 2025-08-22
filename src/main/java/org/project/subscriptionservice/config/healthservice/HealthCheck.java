package org.project.subscriptionservice.config.healthservice;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
@Slf4j
public class HealthCheck {

    private final StringRedisTemplate stringRedisTemplate;
    private final DataSource dataSource;

    @SneakyThrows
    @Scheduled(fixedRate = 60000) // 60 seconds
    public void ServicesCheck(){

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2) ){
                log.info("... Datasource is up :) !! ...");
            }
        }catch (Exception e){
            log.warn("... Datasource is down :( !! ...");
        }

        try {
            assert stringRedisTemplate.getConnectionFactory() != null;
            String redis = stringRedisTemplate.getConnectionFactory().getConnection().ping();
            if (redis != null) {
                log.info("... Redis is up :) !! ...");
            }
        }catch (Exception e){
            log.warn("... Redis is down :( !! ...");
        }
    }
}
