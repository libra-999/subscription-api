package org.project.subscriptionservice.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AutoScheduler {

    private final Scheduler scheduler;

    @PostConstruct
    public void AutoSubscribeScheduleJobs() throws SchedulerException {

        JobKey jobKey = new JobKey("AutoSubscribeJob", "Subscribe");
        TriggerKey triggerKey = new TriggerKey("AutoSubscribeTrigger", "Subscribe");

        JobDetail jobDetail = JobBuilder.newJob(AutoSubscribeJob.class)
            .withIdentity(jobKey)
            .withDescription("Subscriber need to renew your plan")
            .storeDurably()
            .build();

        scheduler.addJob(jobDetail, true); // check exist job to overwrite

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.MONDAY, 0, 0)) // running once per week
            .forJob(jobDetail)
            .build();

        if (!scheduler.checkExists(jobDetail.getKey())) {
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

}
