/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 15:05
 */
package com.acooly.component.content.audit.service;

import com.acooly.component.content.audit.domain.ImageAuditRequest;
import com.acooly.component.content.audit.domain.TextAuditExtRequest;
import com.acooly.component.content.audit.domain.TextAuditExtResponse;
import com.acooly.component.content.audit.domain.TextAuditRequest;

/**
 * @author zhangpu
 * @date 2021-07-23 15:05
 */
public interface ContentAuditService {

    /**
     * 文字审计
     * <p>
     * 使用简介：
     * 本次封装主要是为简化使用，屏蔽接口中复杂的多层结构和逻辑判断。时间一个简单的功能：判断输入文字是否合格如果不合规返回原因。
     * <li>可同时支持多个文字的判断（批量，通过request.addTask）</li>
     * <li>只有所有通过审计才不会抛出异常，否则都抛出异常。</li>
     * <li>异常内容为违规的原因。一般结构为：code:违规label，message是中文说明，detail可选返回问题文字（*号mask）</li>
     * <li>支持配置自定义文字黑白名单（阿里云后台配置）</li>
     *
     * @param request
     * @return
     */
    void textScan(TextAuditRequest request);
    
    
    /**
     * 文字审计(textScan扩展，应对多文本审计，标记未通过文本)
     * <p>
     * 使用简介：
     * 本次封装主要是为简化使用，屏蔽接口中复杂的多层结构和逻辑判断。时间一个简单的功能：判断输入文字是否合格如果不合规返回原因。
     * <li>可同时支持多个文字的判断（批量，通过request.addTask）</li>
     * <li>只有所有通过审计才不会抛出异常，否则都抛出异常。</li>
     * <li>异常内容为违规的原因。一般结构为：code:违规label，message是中文说明，detail可选返回问题文字（*号mask）</li>
     * <li>支持配置自定义文字黑白名单（阿里云后台配置）</li>
     *
     * @param request
     * @return
     */
    TextAuditExtResponse textScanExt(TextAuditExtRequest request);


    /**
     * 图片审计
     * <p>
     * 使用简介：
     * 本次封装主要是为简化使用，屏蔽接口中复杂的多层结构和逻辑判断。时间一个简单的功能：判断输入公网可访问图片URL是否合规，如果不合规返回原因（异常）。
     * <li>可同时支持多个图片URL的判断（批量，通过request.addTask）</li>
     * <li>只有所有通过审计才不会抛出异常，否则都抛出异常。</li>
     * <li>异常内容为违规的原因。一般结构为：code:违规label，message是中文说明</li>
     *
     * @param request
     */
    void imageScan(ImageAuditRequest request);

}
