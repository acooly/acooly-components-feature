/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:12
 */
package com.acooly.component.content.audit.domain;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangpu
 * @date 2021-07-23 21:12
 */
@Slf4j
@Data
@ToString
public class TextAuditResponse {

    private String code;
    private String msg;
    /**
     * 检测对象对应的数据ID。
     * 如果在检测请求参数中传入了dataId，则此处返回对应的dataId。
     */
    private String dataId;

    /**
     * 检测任务的ID。
     */
    private String taskId;

    private String content;

    private String filteredContent;

    private List<TextAuditResult> results;

}
