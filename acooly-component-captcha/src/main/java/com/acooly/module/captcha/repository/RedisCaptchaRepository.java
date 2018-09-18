package com.acooly.module.captcha.repository;

import com.acooly.module.captcha.Captcha;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author shuijing
 */
public class RedisCaptchaRepository implements CaptchaRepository<String, Captcha> {

    private RedisTemplate<String, Captcha> redisTemplate;

    public RedisCaptchaRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Captcha value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Captcha get(String key) {
        return (Captcha) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
