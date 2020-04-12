/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 20:09
 */
package com.acooly.module.smsend.sender;

import com.acooly.module.smsend.enums.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu
 * @date 2020-04-12 20:09
 */
@Component
public class SmsendCounter {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    public static final String MOBILE_COUNT_DAY = SmsendCounter.class.getName() + ".countDay";


    public void incrementSendCount(String mobileNo, SmsProvider smsProvider) {
        ValueOperations<String, Integer> vp = redisTemplate.opsForValue();
        vp.increment(getCountKey(mobileNo, smsProvider));
    }

    public int getSendCount(String mobileNo, SmsProvider smsProvider) {
        Integer count =  redisTemplate.opsForValue().get(getCountKey(mobileNo, smsProvider));
        if (count == null){
            count = 0;
        }
        return count;
    }


    public String getCountKey(String mobileNo, SmsProvider smsProvider) {
        return MOBILE_COUNT_DAY + "." + mobileNo + "." + smsProvider.code();
    }


}
