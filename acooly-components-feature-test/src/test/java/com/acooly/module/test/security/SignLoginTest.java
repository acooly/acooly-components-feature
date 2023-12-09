/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-12-09 22:07
 */
package com.acooly.module.test.security;

import com.acooly.core.utils.Dates;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * 后台BOSS签名测试URL生成
 *
 * @author zhangpu
 * @date 2023-12-09 22:07
 */
@Slf4j
public class SignLoginTest {

    @Test
    public void testSignLogin() {
        String accessKey = "8a48b5514ecd7fXXXXa8014eced85457034c";
        String secretKey = "30cbffc2ad172X40e7bSse2aXc82370250160";
        String mobileNo = "13896177630";
        String timestamp = Dates.format(new java.util.Date(), "yyyyMMddHHmmss");
        String waitForSign = "accessKey=" + accessKey + "&mobileNo=" + mobileNo + "&timestamp=" + timestamp + secretKey;
        String sign = DigestUtils.md5DigestAsHex(waitForSign.getBytes(Charsets.UTF_8));
        String url = "http://127.0.0.1:8090/manage/login.html?accessKey=" + accessKey + "&mobileNo=" + mobileNo + "&timestamp=" + timestamp + "&sign=" + sign;
        System.out.println(url);
    }

}
