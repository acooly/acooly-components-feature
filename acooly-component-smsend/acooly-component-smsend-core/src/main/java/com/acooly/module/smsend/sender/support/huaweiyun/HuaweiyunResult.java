/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-06 14:46
 */
package com.acooly.module.smsend.sender.support.huaweiyun;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangpu
 * @date 2023-07-06 14:46
 */
@Getter
@Setter
public class HuaweiyunResult extends InfoBase {

    private String code;
    private String description;
    private List<SmsId> result;

    public SmsId getFirst() {
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

}
