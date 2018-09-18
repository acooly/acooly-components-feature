package com.acooly.module.scheduler.exceptions;

/**
 * @author shuijing
 */
public class SchedulerEngineException extends RuntimeException {
    public SchedulerEngineException(Exception e) {
        super(e);
    }

    public SchedulerEngineException(String msg) {
        super(msg);
    }

    public SchedulerEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
