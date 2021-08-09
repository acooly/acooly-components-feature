/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 16:17
 */
package com.acooly.component.content.audit.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * @author zhangpu
 * @date 2021-07-23 16:17
 */
@Slf4j
@Data
@NoArgsConstructor
public class TextAuditRequest {

    private String bizType = "default";

    /**
     * 常量antispam表示文本检测
     */
    private String scenes = "antispam";

    private List<TextAuditTask> tasks = Lists.newArrayList();


    public TextAuditRequest(String text) {
        addTask(text);
    }

    public void addTask(String dataId, String text) {
        this.tasks.add(new TextAuditTask(dataId, text));
    }

    public void addTask(String text) {
        this.addTask(UUID.randomUUID().toString(), text);
    }


}
