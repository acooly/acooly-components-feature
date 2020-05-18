/**
 * create by zhangpu date:2015年5月27日
 */
package com.acooly.module.smsend.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信发送扩展参数
 *
 * @author zhangpu
 */
@Getter
@Setter
public class SmsSendParams {

    private String clientIp;
    private String comments;

    public SmsSendParams() {
    }

    public SmsSendParams(String clientIp) {
        this.clientIp = clientIp;
    }

    public SmsSendParams(String clientIp, String comments) {
        this.clientIp = clientIp;
        this.comments = comments;
    }
}
