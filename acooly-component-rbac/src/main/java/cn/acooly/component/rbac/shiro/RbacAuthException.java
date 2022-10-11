/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 18:28
 */
package cn.acooly.component.rbac.shiro;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2022-10-11 18:28
 */
@Slf4j
public class RbacAuthException extends BusinessException {

    public RbacAuthException(Messageable messageable) {
        super(messageable);
    }

    public RbacAuthException(Messageable messageable, String detail) {
        super(messageable, detail);
    }
}
