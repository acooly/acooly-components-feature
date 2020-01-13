/**
 * acooly-core-parent
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-05-09 14:46
 */
package com.acooly.module.ofile.auth;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2019-05-09 14:46
 */
@Slf4j
public class OFileUploadException extends BusinessException {


    public OFileUploadException(Messageable messageable) {
        super(messageable);
    }

    public OFileUploadException(Messageable messageable, String msg) {
        super(messageable, msg);
    }
}
