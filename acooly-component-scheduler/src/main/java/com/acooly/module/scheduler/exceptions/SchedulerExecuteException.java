package com.acooly.module.scheduler.exceptions;

/**
 * @author shuijing
 */
public class SchedulerExecuteException extends RuntimeException {
    public SchedulerExecuteException(Exception e) {
        super(e);
    }

    public SchedulerExecuteException(String msg) {
        super(msg);
    }
}
