package org.project.subscriptionservice.job;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.domain.service.SubService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AutoSubscribeJob implements Job {

    private final SubService subService;

    @Override
    @SneakyThrows
    public void execute(JobExecutionContext jobExecutionContext) {
        subService.checkRenew();
        log.info(" ... Auto-Renew Completed ... ");
    }
}
