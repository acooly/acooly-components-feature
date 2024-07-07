/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-06-07 15:12
 */
package com.acooly.module.test.sse;

import com.acooly.core.common.facade.InfoBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangpu
 * @date 2024-06-07 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SseMessage extends InfoBase {
    /**
     * 客户端id
     */
    private String userId;
    /**
     * 传输数据体(json)
     */
    private String data;
}
