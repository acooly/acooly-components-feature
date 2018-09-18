package com.acooly.module.captcha.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shuijing
 */
public class DefaultLongNumericGenerator implements LongNumericGenerator {
    private static final int MAX_STRING_LENGTH = Long.toString(Long.MAX_VALUE).length();

    private static final int MIN_STRING_LENGTH = 1;

    private final AtomicLong count;

    public DefaultLongNumericGenerator() {
        this(0);
        // nothing to do
    }

    public DefaultLongNumericGenerator(final long initialValue) {
        this.count = new AtomicLong(initialValue);
    }

    @Override
    public long getNextLong() {
        return this.getNextValue();
    }

    @Override
    public String getNextNumberAsString() {
        return Long.toString(this.getNextValue());
    }

    @Override
    public int maxLength() {
        return DefaultLongNumericGenerator.MAX_STRING_LENGTH;
    }

    @Override
    public int minLength() {
        return DefaultLongNumericGenerator.MIN_STRING_LENGTH;
    }

    protected long getNextValue() {
        if (this.count.compareAndSet(Long.MAX_VALUE, 0)) {
            return Long.MAX_VALUE;
        }
        return this.count.getAndIncrement();
    }
}
