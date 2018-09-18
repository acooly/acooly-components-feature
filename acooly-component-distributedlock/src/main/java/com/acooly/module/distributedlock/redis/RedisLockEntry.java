
package com.acooly.module.distributedlock.redis;

import lombok.Data;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * @author shuijing
 */
@Data
public class RedisLockEntry {

    private int counter;

    private final Semaphore latch;
    private final ConcurrentLinkedQueue<Runnable> listeners = new ConcurrentLinkedQueue<Runnable>();

    public RedisLockEntry() {
        super();
        this.latch = new Semaphore(0);
    }

    public void aquire() {
        counter++;
    }

    public int release() {
        return --counter;
    }

}
