package org.project.subscriptionservice.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AutoSubscribeScheduler {

    private final Scheduler scheduler;

    @PostConstruct
    public void AutoSubscribeScheduleJobs() {
    }

}
