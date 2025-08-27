package org.project.subscriptionservice.config.quartz;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig extends AdaptableJobFactory {

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final SpringJobFactory jobFactory;


    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTransactionManager(transactionManager);
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setQuartzProperties(getProperties());
        schedulerFactory.setStartupDelay(5);
        schedulerFactory.setJobFactory(jobFactory);
        return schedulerFactory;
    }

    @Bean
    @SneakyThrows
    public Properties getProperties() {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("/quartz.yml"));
        properties.afterPropertiesSet();
        return properties.getObject();
    }

    @Bean
    public QuartzInitializerListener quartzInitializerListener() {
        return new QuartzInitializerListener();
    }

}
