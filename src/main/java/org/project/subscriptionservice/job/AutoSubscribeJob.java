package org.project.subscriptionservice.job;

import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.domain.service.SubService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoSubscribeJob implements Job {

    private final SubService subService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

}
