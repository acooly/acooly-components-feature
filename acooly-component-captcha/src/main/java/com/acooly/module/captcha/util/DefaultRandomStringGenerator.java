package com.acooly.module.captcha.util;

import java.security.SecureRandom;
import java.util.stream.IntStream;

/**
 * @author shuijing
 */
public class DefaultRandomStringGenerator implements RandomStringGenerator {
    public static final int DEFAULT_MAX_RANDOM_LENGTH = 35;

    private static final char[] PRINTABLE_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679".toCharArray();

    private final SecureRandom randomizer = new SecureRandom();

    private final int maximumRandomLength;

    public DefaultRandomStringGenerator() {
        this.maximumRandomLength = DEFAULT_MAX_RANDOM_LENGTH;
    }

    public DefaultRandomStringGenerator(final int maxRandomLength) {
        this.maximumRandomLength = maxRandomLength;
    }

    private static String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        IntStream.range(0, random.length)
                .forEach(
                        i -> {
                            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
                            output[i] = PRINTABLE_CHARACTERS[index];
                        });

        return new String(output);
    }

    @Override
    public int getMinLength() {
        return this.maximumRandomLength;
    }

    @Override
    public int getMaxLength() {
        return this.maximumRandomLength;
    }

    @Override
    public String getNewString() {
        final byte[] random = getNewStringAsBytes();
        return convertBytesToString(random);
    }

    @Override
    public byte[] getNewStringAsBytes() {
        final byte[] random = new byte[this.maximumRandomLength];
        this.randomizer.nextBytes(random);
        return random;
    }
}
