/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-04 19:53
 */
package com.acooly.component.data.ip.ipblock;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhangpu
 * @date 2021-10-04 19:53
 */
@Slf4j
@Component
public class IpdenyIpBlockLoader implements IpBlockLoader {

    public static final String CACHE_KEY_PREFIX = IpdenyIpBlockLoader.class.getName() + "_IPBLOCKS_";

    @Autowired
    private RedisTemplate<String, List<String>> redisTemplate;

    @Override
    public List<String> load(Locale locale) {
        return doLoad(locale);
    }


    protected List<String> doLoad(Locale locale) {
        String key = getCacheKey(locale);
        List<String> ipblocks = redisTemplate.opsForValue().get(getCacheKey(locale));
        if (Collections3.isEmpty(ipblocks)) {
            String countryCode = Strings.lowerCase(locale.getCountry());
            try {
                String body = HttpRequest.get("http://www.ipdeny.com/ipblocks/data/countries/" + countryCode + ".zone").body();
                ipblocks = Lists.newArrayList(Strings.splitByWholeSeparator(body, Strings.LF));
                ipblocks = ipblocks.stream().filter(ip -> Strings.isNotBlank(ip)).collect(Collectors.toList());
                log.info("load ip blocks(" + countryCode + ") from ipdeny.com");
            } catch (Exception e) {
                throw new BusinessException(CommonErrorCodes.COMMUNICATION_ERROR, e.getMessage());
            }
            if (Collections3.isNotEmpty(ipblocks)) {
                redisTemplate.opsForValue().set(key, ipblocks, 24, TimeUnit.HOURS);
            }
        }
        return ipblocks;
    }


    protected String getCacheKey(Locale locale) {
        return CACHE_KEY_PREFIX + locale.getCountry();
    }


}

