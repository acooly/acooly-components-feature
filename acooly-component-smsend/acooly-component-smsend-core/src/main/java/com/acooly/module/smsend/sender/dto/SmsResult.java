/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-04 00:46
 */
package com.acooly.module.smsend.sender.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.smsend.common.enums.SmsProvider;
import lombok.Getter;
import lombok.Setter;

/**
 * 短信发送结果
 *
 * @author zhangpu
 * @date 2020-05-04 00:46
 */
@Getter
@Setter
public class SmsResult extends InfoBase implements Messageable {

    private String code;
    private String message;
    private String requestId;
    private boolean success = true;
    private SmsProvider provider;
    private String templateProvider;

    public SmsResult() {
    }

    public SmsResult(Messageable messageable, SmsProvider provider) {
        this.code = messageable.code();
        this.message = messageable.message();
        this.provider = provider;
        this.success = false;
    }

    public SmsResult(SmsProvider provider) {
        this.provider = provider;
    }

    public void setErrorCode(Messageable messageable) {
        this.code = messageable.code();
        this.message = messageable.message();
        this.success = false;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
