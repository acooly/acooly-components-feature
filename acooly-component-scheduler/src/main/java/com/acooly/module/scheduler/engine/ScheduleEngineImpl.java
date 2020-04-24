package com.acooly.module.scheduler.engine;

import com.acooly.core.utils.Exceptions;
import com.acooly.module.scheduler.dao.SchedulerRuleDao;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.exceptions.SchedulerEngineException;
import com.acooly.module.scheduler.executor.TaskStatusEnum;
import com.acooly.module.scheduler.job.QuartzJob;
import com.acooly.module.scheduler.service.SchedulerRuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author shuijing
 */
@Service
public class ScheduleEngineImpl implements ScheduleEngine {

    private static final String JOB_PREFIX = "job";
    private static final String TRIGGER_PREFIX = "trigger";
    private static Logger logger = LoggerFactory.getLogger(ScheduleEngineImpl.class);
    @Autowired
    private SchedulerRuleDao schedulerRuleRepository;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SchedulerRuleService schedulerRuleService;
    @Autowired
    private ObjectMapper objectMapper;
    private Object schedulerLock = new Object();

    public static String getLogPrefix(Long id) {
        return "[task" + id + "]";
    }

    @Override
    public Long addJobToEngine(SchedulerRule rule) {
        try {
            addJobOrUpdateTriggerToEngine(rule);
        } catch (Exception e) {
            throw new SchedulerEngineException(getLogPrefix(rule.getId()) + "启动失败:" + e.getMessage());
        }
        return rule.getId();
    }

    @Override
    public void update(SchedulerRule rule) {
        try {
            addJobOrUpdateTriggerToEngine(rule);
        } catch (Exception e) {
            throw new SchedulerEngineException(getLogPrefix(rule.getId()) + "更新失败:" + e.getMessage());
        }
    }

    @Override
    public void deleteJob(SchedulerRule ruleDO) {
        try {
            JobKey jobKey = new JobKey(getJobKey(ruleDO), getGroupKey(ruleDO));
            synchronized (schedulerLock) {
                if (scheduler != null) {
                    if (scheduler.getJobDetail(jobKey) != null) {
                        scheduler.deleteJob(jobKey);
                        logger.info("{}从quartz引擎中删除", getLogPrefix(ruleDO.getId()));
                    }
                }
            }
        } catch (SchedulerException e) {
            throw Exceptions.runtimeException(getLogPrefix(ruleDO.getId()) + "删除失败:" + e.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            synchronized (schedulerLock) {
                if (scheduler != null) {
                    scheduler.shutdown(true);
                }
            }
            logger.info("quartz服务关闭成功");
        } catch (SchedulerException e) {
            logger.error("停止调度引擎失败", e);
            throw Exceptions.runtimeException(e);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
                init();
            }
        } catch (SchedulerException e) {
            throw new SchedulerEngineException("启动时候初始任务失败!", e);
        }
    }

    private void init() {
        List<SchedulerRule> schedulerRules = schedulerRuleService.getAll();
        schedulerRules.forEach(
                schedulerRule -> {
                    if (schedulerRule.getStatus().equals(TaskStatusEnum.NORMAL.getCode()))
                        addJobToEngine(schedulerRule);
                });
    }

    /**
     * 添加任务或者更新到quarz引擎
     */
    public void addJobOrUpdateTriggerToEngine(final SchedulerRule rule) throws Exception {

        Long taskId = rule.getId() == null ? -1 : rule.getId();
        String logPrefix = getLogPrefix(taskId);

        if (!scheduler.isStarted()) {
            throw Exceptions.runtimeException(logPrefix + "启动失败:quartz服务没有启动.");
        }

        validateRule(rule);

        Date sysdate = schedulerRuleRepository.getSysdate();

        final String jobKey = getJobKey(rule);
        final String groupKey = getGroupKey(rule);
        final String triggerKey = getTriggerKey(rule);

        TriggerKey triggerGroupKey = TriggerKey.triggerKey(triggerKey, groupKey);

        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerGroupKey);

        JobDetail job =
                JobBuilder.newJob(QuartzJob.class)
                        .storeDurably(true)
                        .withIdentity(jobKey, groupKey)
                        .build();

        //注意,当存储到db时候,JobDataMap只支持string. 切勿存储有复杂对象引用bean，集群时解析bean会失败
        job.getJobDataMap().put(QuartzJob.SCHEDULER_RULE_DO_KEY, objectMapper.writeValueAsString(rule));

        Trigger newTrigger;
        // 在重启 或者宕机时候处理 Misfired 任务
        // 策略为什么都不做 withMisfireHandlingInstructionDoNothing
        //Trigger不存在，创建一个
        if (null == oldTrigger) {
            Set<Trigger> triggerSet = new HashSet<>();
            if (rule.getValidityStart().before(sysdate)) {
                newTrigger =
                        newTrigger()
                                .withIdentity(triggerKey, groupKey)
                                .withSchedule(
                                        cronSchedule(rule.getCronString()).withMisfireHandlingInstructionDoNothing())
                                .endAt(rule.getValidityEnd())
                                .build();
            } else {
                newTrigger =
                        newTrigger()
                                .withIdentity(triggerKey, groupKey)
                                .withSchedule(
                                        cronSchedule(rule.getCronString()).withMisfireHandlingInstructionDoNothing())
                                .startAt(rule.getValidityStart())
                                .endAt(rule.getValidityEnd())
                                .build();
            }

            triggerSet.add(newTrigger);

            try {
                synchronized (schedulerLock) {
                    if (scheduler != null) {
                        scheduler.scheduleJob(job, triggerSet, true);
                    }
                }
                logger.info("{} 添加到quartz引擎.", logPrefix);
            } catch (SchedulerException e) {
                e.printStackTrace();
                throw new SchedulerEngineException(logPrefix + "启动失败:" + e.getMessage());
            }

        } else {
            // Trigger已存在，那么更新相应的定时设置
            // 按新的trigger重新设置job执行
            if (rule.getValidityStart().before(sysdate)) {
                newTrigger =
                        oldTrigger
                                .getTriggerBuilder()
                                .withIdentity(triggerKey, groupKey)
                                .withSchedule(
                                        cronSchedule(rule.getCronString()).withMisfireHandlingInstructionDoNothing())
                                .endAt(rule.getValidityEnd())
                                .build();
            } else {
                newTrigger =
                        oldTrigger
                                .getTriggerBuilder()
                                .withIdentity(triggerKey, groupKey)
                                .withSchedule(
                                        cronSchedule(rule.getCronString()).withMisfireHandlingInstructionDoNothing())
                                .startAt(rule.getValidityStart())
                                .endAt(rule.getValidityEnd())
                                .build();
            }
            try {
                synchronized (schedulerLock) {
                    if (scheduler != null) {
                        //更新job
                        scheduler.addJob(job, true);
                        scheduler.rescheduleJob(triggerGroupKey, newTrigger);
                    }
                }
                logger.info("{} 添加到quartz引擎.", logPrefix);
            } catch (SchedulerException e) {
                throw new SchedulerEngineException(logPrefix + "启动失败:" + e.getMessage());
            }
        }
    }

    @Override
    public void validateRule(SchedulerRule rule) {
        Long taskId = rule.getId() == null ? -1 : rule.getId();
        String logPrefix = getLogPrefix(taskId);

        if (StringUtils.isBlank(rule.getCronString())) {
            throw new SchedulerEngineException(logPrefix + "启动失败:corn表达式不能为空.");
        } else {
            try {
                CronExpression.validateExpression(rule.getCronString());
            } catch (ParseException e) {
                throw new SchedulerEngineException(logPrefix + "启动失败:corn表达式解析失败:" + e.getMessage(), e);
            }
        }

        Date validityEnd = rule.getValidityEnd();

        if (validityEnd == null) {
            throw new SchedulerEngineException(logPrefix + "启动失败:validityEnd不能为空.");
        }

        Date sysdate = schedulerRuleRepository.getSysdate();

        if (validityEnd.before(sysdate)) {
            //启动的时候不需要检查
            logger.info(logPrefix + "任务结束时间比当前时间早，不添加到quartz引擎");
            //throw new SchedulerEngineException(logPrefix + "启动失败:结束时间比当前时间早");
        }
    }

    public boolean isJobExisted(SchedulerRule ruleDO) throws SchedulerException {
        JobKey jobKey = new JobKey(getJobKey(ruleDO), getGroupKey(ruleDO));
        if (scheduler != null) {
            if (scheduler.getJobDetail(jobKey) != null) {
                return true;
            }
        }
        return false;
    }

    private String getGroupKey(final SchedulerRule rule) {
        return "acooly";
    }

    private String getJobKey(final SchedulerRule rule) {
        return JOB_PREFIX + rule.getId();
    }

    private String getTriggerKey(final SchedulerRule rule) {
        return TRIGGER_PREFIX + rule.getId();
    }
}
