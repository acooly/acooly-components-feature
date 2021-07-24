/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:23
 */
package cn.acooly.component.content.audit.domain;

import com.acooly.core.common.facade.DtoBase;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2021-07-23 21:23
 */
@Slf4j
@Data
@ToString
public class TextAuditResult extends DtoBase {

    /**
     * 检测场景，和调用请求中的场景对应
     */
    private String scene;

    /**
     * 建议您执行的后续操作。取值
     * pass：文本正常，可以直接放行。
     * review：文本需要进一步人工审核。
     * block：文本违规，可以直接删除或者限制公开。
     */
    private String suggestion;

    /**
     * 文本垃圾检测结果的分类。取值：
     * normal：正常文本
     * spam：含垃圾信息
     * ad：广告
     * politics：涉政
     * terrorism：暴恐
     * abuse：辱骂
     * porn：色情
     * flood：灌水
     * contraband：违禁
     * meaningless：无意义
     * customized：自定义（例如命中自定义关键词）
     */
    private String label;

    /**
     * 置信度分数，取值范围：0（表示置信度最低）~100（表示置信度最高）。
     * 如果suggestion为pass，则置信度越高，表示内容正常的可能性越高；如果suggestion为review或block，则置信度越高，表示内容违规的可能性越高。
     */
    private Float rate;

    /**
     * 附加信息，扩展字段。
     */
    private String extras;

    private String details;

}
