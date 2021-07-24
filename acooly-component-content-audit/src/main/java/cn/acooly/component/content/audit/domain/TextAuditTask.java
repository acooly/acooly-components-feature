/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 22:01
 */
package cn.acooly.component.content.audit.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2021-07-23 22:01
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextAuditTask {

    /**
     * 检测对象对应的数据ID。
     * 由大小写英文字母、数字、下划线（_）、短划线（-）、英文句号（.）组成，不超过128个字符，可以用于唯一标识您的业务数据。
     */
    private String dataId;

    /**
     * 待检测文本，最长10000个中文字符（包含标点）。
     */
    private String content;
}
