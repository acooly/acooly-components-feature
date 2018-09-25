package com.acooly.core.test.utils;

import com.acooly.core.utils.Hashs;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-25 12:39
 */
@Slf4j
public class HashingTest {


    @Test
    public void testHash() {
        String url = "https://www.javacodegeeks.com/2014/08/url-shortener-service-in-42-lines-of-code-in-java-spring-boot-redis.html";
        log.info("{}", Hashs.hash(url));
    }

}
