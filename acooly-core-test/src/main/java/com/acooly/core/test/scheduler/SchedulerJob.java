package com.acooly.core.test.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerJob {
    public void exe() {
        log.info("run in quartz!");
    }
}
