/**
 * create by zhangpu date:2015年5月27日
 */
package com.acooly.module.sms.sender.impl;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MOCK实现
 *
 * @author zhangpu
 */
@Service("mockShortMessageSender")
public class MockShortMessageSender extends AbstractShortMessageSender {

    @Override
    public String send(String mobileNo, String content) {
        logger.info("MOCK Send: mobileNo:{}, content:{}", mobileNo, content);
        return "0";
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        logger.info("MOCK Send: mobileNos:{}, content:{}", mobileNos, content);
        return "0";
    }

    @Override
    public String getProvider() {
        return "模拟发送";
    }
}
