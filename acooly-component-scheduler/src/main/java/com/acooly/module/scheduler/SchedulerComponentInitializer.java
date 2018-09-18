package com.acooly.module.scheduler;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author shuijing
 */
public class SchedulerComponentInitializer implements ComponentInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing(
                "acooly.ds.Checker.excludedColumnTables.scheduler",
                "QRTZ_CALENDARS, QRTZ_JOB_DETAILS,QRTZ_TRIGGERS,QRTZ_BLOB_TRIGGERS,QRTZ_CRON_TRIGGERS,QRTZ_SIMPROP_TRIGGERS,QRTZ_SIMPLE_TRIGGERS,QRTZ_LOCKS,QRTZ_SCHEDULER_STATE,QRTZ_PAUSED_TRIGGER_GRPS,QRTZ_FIRED_TRIGGERS");
    }
}
